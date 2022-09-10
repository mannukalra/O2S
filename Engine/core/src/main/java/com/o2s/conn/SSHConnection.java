package com.o2s.conn;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.o2s.util.RegexUtil;

public class SSHConnection implements Connection {

    Session session;
    

    public SSHConnection(String host, String user, String password) {
        var config = new Properties(); 
        config.put("StrictHostKeyChecking", "no");
        try {
            this.session = (new JSch()).getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
        }catch(JSchException e){
            e.printStackTrace();//TODO error and session disconnect
        }
        
        System.out.println("Connected");
    }


    @Override
    public String discoverOS(){
        String result = runCommand("egrep '^(NAME|VERSION)=' /etc/os-release");//TODO read cmds and patterns from props
        if(result == null || "".equals(result) ){
            result = runCommand("powershell -command (Get-WmiObject -class Win32_OperatingSystem).Caption");
        }else{
            String name = RegexUtil.findMatch(result, "(?<=NAME=\")(.*)(?=\")", 1);
            String version = RegexUtil.findMatch(result, "(?<=VERSION=\")([0-9.]+)", 1);
            if(name != null && version != null)
                result = name +" "+ version;
        }
        return result;
    }


    @Override
    public String runCommand(String cmd) {
        String result = null;

        Channel channel = null;
        try{
            if(session.isConnected()){
                channel = session.openChannel("exec");
                ((ChannelExec)channel).setCommand(cmd);
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                
                var stream = channel.getInputStream();
                channel.connect();

                result = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
                
            }
        }catch(JSchException | IOException e){
            e.printStackTrace(); //TODO
        }finally{
           if(channel != null) channel.disconnect();
        }
        return result;
    }
    
}
