add-type @"
    using System.Net;
    using System.Security.Cryptography.X509Certificates;
    public class TrustAllCertsPolicy : ICertificatePolicy {
        public bool CheckValidationResult(
            ServicePoint srvPoint, X509Certificate certificate,
            WebRequest request, int certificateProblem) {
            return true;
        }
    }
"@
[System.Net.ServicePointManager]::CertificatePolicy = New-Object TrustAllCertsPolicy
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
Invoke-WebRequest -UseBasicParsing http://192.168.1.36:8090/config -OutFile telegraf/telegraf.conf

#get id from below cmd and kill 
Get-Process telegraf 
#wait 5 sec 

Start-Process -WorkingDirectory "C:\Users\Administrator\WIN-5AQBA3PBPPH.mshome.net\o2s\telegraf" -FilePath .\telegraf.exe -ArgumentList "--config", "telegraf.conf", "> telegraf.log"

