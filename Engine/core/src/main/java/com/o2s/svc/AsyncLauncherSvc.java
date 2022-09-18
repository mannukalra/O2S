package com.o2s.svc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.o2s.conn.Connection;
import com.o2s.data.enm.DeviceType;

@Service
public class AsyncLauncherSvc {

    static Map<String, Boolean> hostCopyStatus = new HashMap<>();

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
    public void copyFile(String host, Connection conn, String sourcePath, String targetPath, DeviceType type){
        var done = false;
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>   hostCopyStatus >>>>>>>>>>>>>>>>>>>"+hostCopyStatus);
            conn.copyFile(sourcePath, targetPath, type);
            System.out.println(Thread.currentThread().getId()+" *********************************************** "+Thread.currentThread().getName());
            done = true;
        }catch(Exception e){
            done = false;
        }
        hostCopyStatus.put(host, done);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>   hostCopyStatus >>>>>>>>>>>>>>>>>>>"+hostCopyStatus);
    }

}
