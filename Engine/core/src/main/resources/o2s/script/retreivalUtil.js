
// O2S JS to be called using nashorn
function typeRetreivalConfig(asJson){
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
    return asJson ? config : JSON.stringify(config);
}

function discoverType(connection, device){
    var result = "";
    var osConfig = typeRetreivalConfig(true);
    
    for(var index in osConfig){
        var conf = osConfig[index];
        try {
            var cmdOutput = connection.executeCommand(conf["command"]);
            if(cmdOutput != null && !cmdOutput.equals("")){
                if(conf["regex"]){
                    var regexPatterns = conf["regex"];
                    for(var indx in regexPatterns){
                        var ptrnEntry = regexPatterns[indx];
                        var match = com.o2s.util.RegexUtil.findMatch(cmdOutput, ptrnEntry["pattern"], ptrnEntry["targetGroup"]);
                        if(ptrnEntry["key"] == "name"){//TODO Better design for name/version retrieval/setting
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
                device.setType(com.o2s.data.enm.DeviceType.valueOf(conf.type));
                break;
            }
        } catch (ex) {
            print("Failed to execute command: "+conf["command"]+", Error details:", ex.message);
        }
    }
    
    if(!result){
        print("Failed to fetch OS details, refer logs for details");
    }
}

function validateBasePath(conn, device){
    var basePath = null;
    if(device.getType() == com.o2s.data.enm.DeviceType.LINUX){
        var targetdir = "~"+ device.getUser() +"/"+ device.getHost() +"/o2s";
        basePath = conn.executeCommand("mkdir -p "+targetdir+";cd "+targetdir+";pwd");
          
    }else if(device.getType() == com.o2s.data.enm.DeviceType.WINDOWS){
        var targetdir = "%userprofile%\\"+device.getHost()+"\\o2s";
        basePath = conn.executeCommand("mkdir "+targetdir+" 2> NUL & cd "+targetdir+" & cd");
    }

    if(basePath){
        device.setBasePath(basePath);
    }else{
        print("Failed to setup/validate base path, refer logs for details");
    }
}

function validateO2SAccess(conn, device, sourcePath, fileName){
    var targetFilePath = device.getBasePath()+"/"+fileName;
    conn.copyFile(sourcePath+"/"+fileName, targetFilePath, device.getType());
    var result="";
    try{
        result = conn.executeScript(targetFilePath, device.getType());
        print("result  >>>>>>>>>>> "+result);
    }catch(ex){print(ex.message);}

    conn.deleteFile(targetFilePath, device.getType());
    return result;
}
