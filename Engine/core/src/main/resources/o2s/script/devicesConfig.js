// O2S JS to be called using nashorn
function devicesConfig(asJson){
    var config = [
        {
            type: "LINUX",
            subtype: ["UBUNTU", "RHEL"],
            os_command: "egrep '^(NAME|VERSION)=' /etc/os-release",
            os_regex: [
                { key: "name", pattern: "(?<=NAME=\")(.*)(?=\")", targetGroup: 1 },
                { key: "version", pattern: "(?<=VERSION=\")([0-9.]+)", targetGroup: 1 }
            ],
            
        },
        {
            type: "WINDOWS",
            os_command: "powershell -command (Get-WmiObject -class Win32_OperatingSystem).Caption"
        }
    ];
    return asJson ? config : JSON.stringify(config);
}