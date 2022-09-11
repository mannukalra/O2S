package com.o2s.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.enm.DeviceType;
import com.o2s.util.RegexUtil;
import com.o2s.util.nashorn.NHEngine;

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


    
    public String discoverOS_old(){//todo cleanup
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
    public void discoverOS(DeviceDto device){
        String result = "";
        var osConfig = NHEngine.getOsRetreivalConfig();
        for(var conf : osConfig){
            try {
                var cmdOutput = runCommand((String)conf.get("command"));
                if(cmdOutput != null && !cmdOutput.equals("")){//check nonzero exit status
                    if(conf.containsKey("regex")){
                        var regexPatterns = (List<Map<String, Object>>)conf.get("regex");
                        for(var ptrnEntry : regexPatterns){
                            String match = RegexUtil.findMatch(cmdOutput, (String)ptrnEntry.get("pattern"), ((Double)ptrnEntry.get("targetGroup")).intValue() );
                            if(((String)ptrnEntry.get("key")).equals("name")){
                                result = match +" "+ result;
                            }else{
                                result = result+" "+match;
                            }
                        }
                        result = result.replace("  ", " ");
                    }else{
                        result = cmdOutput;
                    }
                }

                if(!result.equals("")){
                    device.setOs(result);
                    device.setType(DeviceType.valueOf((String)conf.get("type")));
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // handle exception
            }
        }
        
        if(result.equals("") ){
            //log error
        }
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
                
                try (var stream = channel.getInputStream();
                    var errStream = channel.getExtInputStream()){

                    channel.connect();
                    result = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
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

    public void configureBasePath(DeviceDto device){
        String basePath = null;
        if(device.getType() == DeviceType.LINUX){
            var targetdir = "~"+ device.getUser() +"/"+ device.getHost() +"/o2s";
            basePath = runCommand("mkdir -p "+targetdir+";cd "+targetdir+";pwd");
              
        }else if(device.getType() == DeviceType.WINDOWS){
            var targetdir = "%userprofile%\\"+device.getHost()+"\\o2s";
            basePath = runCommand("mkdir "+targetdir+" 2> NUL & cd "+targetdir+" & cd");
        }

        if(basePath != null && !basePath.equals(""))
            device.setBasePath(basePath);
    }
    
}
