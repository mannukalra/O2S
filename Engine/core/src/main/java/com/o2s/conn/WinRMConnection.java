package com.o2s.conn;


import org.apache.http.client.config.AuthSchemes;

import com.o2s.data.enm.DeviceType;

import io.cloudsoft.winrm4j.client.WinRmClientContext;
import io.cloudsoft.winrm4j.winrm.WinRmTool;
import io.cloudsoft.winrm4j.winrm.WinRmToolResponse;

public class WinRMConnection implements Connection{
    
    WinRmClientContext context;
    WinRmTool tool;


    public WinRMConnection(String host, String user, String password) {
        context = WinRmClientContext.newInstance();
        try { 
            tool = WinRmTool.Builder.builder(host, user, password)
                // .disableCertificateChecks(true)
                .authenticationScheme(AuthSchemes.NTLM)
                .port(5985)
                .useHttps(false)
                .context(context)
                .build();
            tool.setConnectionTimeout(5000l);
            tool.setOperationTimeout(5000l);
        }catch(Exception e){
            e.printStackTrace();//TODO  add props for secure winrm and ex handling and session timeout
        }
        
        System.out.println("Connected");
    }

   
    @Override
    public void runCommand(String cmd) {
        //implement
    }

    @Override
    public String executeCommand(String cmd) throws NonZeroExitStatusException {
        WinRmToolResponse winRmResponse = tool.executeCommand(cmd);
        var result = winRmResponse.getStdOut().trim();
        if(winRmResponse.getStatusCode() != 0){
            throw new NonZeroExitStatusException("NonZeroExitStatus error: "+ winRmResponse.getStdErr());
        }
        context.shutdown();
        // System.out.println("winRmResponse: code=" + winRmResponse.getStatusCode() + "; out=" + winRmResponse.getStdOut() + "; err=" + winRmResponse.getStdErr());
        return result;
    }

    @Override
    public void copyFile(String sourcePath, String targetPath, DeviceType type) {
        
        targetPath = "/"+(targetPath.replace("\\", "/"));
        // Copy file command
    }

    @Override
    public String executeScript(String path, DeviceType type) throws NonZeroExitStatusException {
        String result = null;
        var executeCmd = "powershell -File ";
        path = path.replace("/", "\\");
        
        result = executeCommand(executeCmd + path);
        return result;
    }

    @Override
    public boolean deleteFile(String path, DeviceType type){
        boolean deleted = false;
        var deleteCmd = "del "+(path.replace("/", "\\"));
        try{
            executeCommand(deleteCmd);
            deleted = false;
        }catch(Exception ex){
            System.out.println("Failed to delete file, error- "+ex.getMessage());
        }

        return deleted;
    }

    @Override
    public void close() throws Exception {
        if(context != null){
            context.shutdown();
        }
    }
    
    // public static void main(String[] args) {
    //     System.out.println(new WinRMConnection("WIN-5AQBA3PBPPH.mshome.net", "Administrator", "").executeCommand("[System.Environment]::OSVersion"));
    // }
    
}
