package com.o2s.conn;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnection implements Connection {

    Session session;
    

    public SSHConnection(String host, String user, String password) {
        var config = new Properties(); 
        config.put("StrictHostKeyChecking", "no");
        try {
            this.session = (new JSch()).getSession(user, host, 22);
            session.setTimeout(3000);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
        }catch(JSchException e){
            e.printStackTrace();//TODO error and session timeout
        }
        
        System.out.println("Connected");
    }

    @Override
    public void runCommand(String cmd) {
        //implement
    }

    @Override
    public String executeCommand(String cmd) {
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
                        throw new RuntimeException("NonZeroExitStatus error: "+err);
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
    public void copyFile(File file, String path) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() throws Exception {
        if(session != null && session.isConnected())
            session.disconnect();
    }
    
}
