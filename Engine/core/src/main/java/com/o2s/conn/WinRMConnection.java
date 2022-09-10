package com.o2s.conn;

import org.apache.http.client.config.AuthSchemes;
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
            e.printStackTrace();//TODO  add props for secure winrm and ex handling and session disconnect
        }
        
        System.out.println("Connected");
    }

    @Override
    public String discoverOS(){
        var result = runCommand("(Get-WmiObject -class Win32_OperatingSystem).Caption");
        return result;
    }

    @Override
    public String runCommand(String cmd) {
        WinRmToolResponse winRmResponse = tool.executePs(cmd);
        var result = winRmResponse.getStdOut();
        context.shutdown();
        // System.out.println("winRmResponse: code=" + winRmResponse.getStatusCode() + "; out=" + winRmResponse.getStdOut() + "; err=" + winRmResponse.getStdErr());
        return result;
    }
    
    
    public static void main(String[] args) {
        System.out.println(new WinRMConnection("WIN-5AQBA3PBPPH.mshome.net", "Administrator", "").runCommand("[System.Environment]::OSVersion"));
    }
    
}
