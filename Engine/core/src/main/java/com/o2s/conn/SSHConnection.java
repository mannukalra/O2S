package com.o2s.conn;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;


import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.o2s.conn.ex.NonZeroExitStatusException;
import com.o2s.data.enm.DeviceType;

public class SSHConnection implements Connection {

    Session session;

    
    public SSHConnection(String host, String user, String password) throws JSchException{
        var config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        
        this.session = (new JSch()).getSession(user, host, 22);
        session.setTimeout(3000);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();
        
        System.out.println("Connected");
    }

    @Override
    public void runCommand(String cmd) {
        //implement
    }

    @Override
    public String executeCommand(String cmd) throws NonZeroExitStatusException{
        String result = null;
        Channel channel = null;

        try{
            if(session.isConnected()){
                channel = session.openChannel("exec");
                ((ChannelExec)channel).setCommand(cmd);
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                
                try (var stream = channel.getInputStream();
                    var errStream = channel.getExtInputStream()){

                    channel.connect();
                    result = CharStreams.toString(new InputStreamReader(stream, "UTF-8")).trim();
                    var err = CharStreams.toString(new InputStreamReader(errStream, "UTF-8"));
                    System.out.println("RESULT:>>"+result+"<< ERR:>>"+err+"<<");
                    if("".equals(result) && !"".equals(err))
                        throw new NonZeroExitStatusException("NonZeroExitStatus error: "+err);
                }
            }
        }catch(JSchException | IOException e){
            e.printStackTrace(); //TODO
        }finally{
            if(channel != null){
                channel.disconnect();
            }
        }
        return result;
    }

    @Override
    public void copyFile(String sourcePath, String targetPath, DeviceType type) {
        
        var updatePermissions = true;
        if(type == DeviceType.WINDOWS){
            updatePermissions = false;
            targetPath = "/"+(targetPath.replace("\\", "/"));
        }
        ChannelSftp sftpChannel;
        try{
            sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            sftpChannel.put(sourcePath, targetPath);

            if(updatePermissions)
                executeCommand("chmod 774 "+targetPath);
            
        }catch(JSchException | SftpException | NonZeroExitStatusException ex){
            ex.printStackTrace();//
        }
    }

    @Override
    public String executeScript(String path, DeviceType type) throws NonZeroExitStatusException{
        String result = null;
        var executeCmd = ". ";
        if(type == DeviceType.WINDOWS){
            executeCmd = "powershell -File ";
            path = path.replace("/", "\\");
        }
        result = executeCommand(executeCmd + path);
        return result;
    }

    @Override
    public boolean deleteFile(String path, DeviceType type){
        boolean deleted = false;

        var deleteCmd = "rm " + path;
        if(type == DeviceType.WINDOWS){
            deleteCmd = "del "+(path.replace("/", "\\"));
        }
        try{
            executeCommand(deleteCmd);
            deleted = false;
        }catch(Exception ex){
            System.out.println("Failed to delete file, error- "+ex.getMessage());
        }

        return deleted;
    }

    @Override
    public String mkDir(String targetDir, DeviceType type) throws NonZeroExitStatusException{
        String result = null;
        if(type == com.o2s.data.enm.DeviceType.LINUX){
            result = executeCommand("mkdir -p "+targetDir+";cd "+targetDir+";pwd");    
        }else if(type == com.o2s.data.enm.DeviceType.WINDOWS){
            result = executeCommand("mkdir "+targetDir+" 2> NUL & cd "+targetDir+" & cd");
        }

        return result;
    }

    @Override
    public boolean extractFile(String basePath, String fileName, String targetFolder, DeviceType type) {
        var separator = type == DeviceType.WINDOWS ? "\\" : "/";
        var zipFile = basePath + separator + fileName;
        var outPath = basePath + separator + targetFolder;
        
        try {
            // mkDir(outPath, type);
            var extractCmd = "tar -xf "+ zipFile +" -C "+ basePath;
            var renameCmd = "mv ";
            var deleteCmd = "rm " + zipFile;
            var rmDirCmd = "rm -rf "+outPath;
            var cmdSeparator = ";";
            if(type == DeviceType.WINDOWS){
                extractCmd = "powershell -Command \"Add-Type -assembly \'system.io.compression.filesystem\';[IO.Compression.ZipFile]::ExtractToDirectory('"+zipFile+"', '"+basePath+"'\")";
                renameCmd = "move ";
                deleteCmd = "del " + zipFile;
                cmdSeparator = " & ";
                rmDirCmd = "rmdir /Q /S "+outPath;
            }
            var result = executeCommand(rmDirCmd + cmdSeparator + extractCmd);
            System.out.println("extraction succeeded! "+result);
            result = executeCommand(deleteCmd + cmdSeparator + renameCmd + outPath+"-* "+outPath);

        } catch (Exception e) {
            return false;
        }       

        return true;
    }
    
    @Override
    public void close() throws Exception {
        if(session != null && session.isConnected())
            session.disconnect();
    }

}
