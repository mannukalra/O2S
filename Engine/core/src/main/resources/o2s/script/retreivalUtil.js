
// JavaScript to be called using nashorn

function eval(device){
    var result = "Welcome to nashorn!!! dear machine "+ device.getAlias();
    
    print(result);
    return JSON.stringify({result: result, host: device.getHost()});
}

function osRetreivalConfig(){
    // entry sequence decides the retrieval order try in case of ssh connection, 
    // ex: Linux then Windows if linux commands fail to return valid os
    var config = [{
        type: "LINUX",
        command: "egrep '^(NAME|VERSION)=' /etc/os-release",
        distros: ["UBUNTU", "RHEL"],
        regex: [
            { key: "name", pattern: "(?<=NAME=\")(.*)(?=\")", targetGroup: 1 },
            { key: "version", pattern: "(?<=VERSION=\")([0-9.]+)", targetGroup: 1 }
        ]
    },
    {
        type: "WINDOWS",
        command: "powershell -command (Get-WmiObject -class Win32_OperatingSystem).Caption"
    }];

    return JSON.stringify(config);
}