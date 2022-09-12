#[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
#[System.Net.ServicePointManager]::ServerCertificateValidationCallback = {$true}

if (-not ([System.Management.Automation.PSTypeName]'ServerCertificateValidationCallback').Type){
$certCallback = @"
    using System;
    using System.Net;
    using System.Net.Security;
    using System.Security.Cryptography.X509Certificates;
    public class ServerCertificateValidationCallback
    {
        public static void Ignore()
        {
            if(ServicePointManager.ServerCertificateValidationCallback ==null)
            {
                ServicePointManager.ServerCertificateValidationCallback += 
                    delegate
                    (
                        Object obj, 
                        X509Certificate certificate, 
                        X509Chain chain, 
                        SslPolicyErrors errors
                    )
                    {
                        return true;
                    };
            }
        }
    }
"@
    Add-Type $certCallback
}
[ServerCertificateValidationCallback]::Ignore()

Invoke-WebRequest -UseBasicParsing https://192.168.1.36:8443/monitor/test/TESTHOST