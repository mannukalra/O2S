package com.o2s.svc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.o2s.conn.ConnectionFactory;
import com.o2s.data.dto.DeviceDto;
import com.o2s.util.Consts;

@Service
public class AsyncLauncherSvc {

    static Cache<String, Boolean> agentCopyStatus = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private ThreadPoolTaskExecutor taskExecutor;

    public AsyncLauncherSvc(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    
    public <T> T launch(Callable<T> callable) {
        Future<T> futureResults = taskExecutor.submit(callable);
        T result = null;
        try {
            result = futureResults.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Async("taskExecutor")
    public void copyAndExtractFile(DeviceDto device){
        var done = false;
        try(var conn = ConnectionFactory.createConnection(device);){
            var agentFileName = Consts.getAgentFileName(device.getType());
            var agentSourcePath = Consts.AGENT_FILE_SOURCE_PATH;
            var targetPath = device.getBasePath()+"/"+agentFileName;
            
            conn.copyFile(agentSourcePath+"/"+agentFileName, targetPath, device.getType());

            conn.extractFile(device.getBasePath(), agentFileName, Consts.AGENT_TARGET_FOLDER, device.getType());
            done = true;
        }catch(Exception e){
            done = false;
        }
        agentCopyStatus.put(device.getHost(), done);
    }

}
