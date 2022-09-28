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
Invoke-WebRequest -UseBasicParsing http://192.168.1.36:8090/config -OutFile %BASE_PATH%\telegraf\telegraf.conf


$ErrorActionPreference = 'SilentlyContinue'
Stop-Process -ID (Get-Process -Name telegraf | Select -ExpandProperty ID) -Force
Start-Sleep -Seconds 3

Start-Process -WorkingDirectory "%BASE_PATH%\telegraf" -FilePath .\telegraf.exe -ArgumentList "--config", "telegraf.conf"

