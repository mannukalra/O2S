package com.o2s.conn;

import java.io.File;

import org.apache.http.client.config.AuthSchemes;

import com.o2s.data.dto.DeviceDto;

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
    public String executeCommand(String cmd) {
        WinRmToolResponse winRmResponse = tool.executeCommand(cmd);
        var result = winRmResponse.getStdOut();
        context.shutdown();
        // System.out.println("winRmResponse: code=" + winRmResponse.getStatusCode() + "; out=" + winRmResponse.getStdOut() + "; err=" + winRmResponse.getStdErr());
        return result;
    }
    
    public void configureBasePath(DeviceDto device){
        //implement
    }


    @Override
    public void copyFile(String sourcePath, String targetPath, boolean updatePermissions) {
        // TODO Auto-generated method stub
        
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
