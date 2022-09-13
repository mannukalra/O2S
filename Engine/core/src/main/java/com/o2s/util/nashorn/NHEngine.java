package com.o2s.util.nashorn;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
// import javax.script.Bindings;
// import com.o2s.data.dto.DeviceDto;
import com.o2s.conn.Connection;
import com.o2s.data.dto.DeviceDto;
import com.o2s.data.enm.DeviceType;

public class NHEngine {
    //cleanup
    public List<Map<String, Object>> getTypeRetreivalConfig(){
        List<Map<String, Object>> config = null;

        ScriptEngineFactory sef = new NashornScriptEngineFactory();
        ScriptEngine nashornEngine = sef.getScriptEngine();

        try {//TODO fileName as config
            nashornEngine.eval(new FileReader("D:\\DND\\VSCode\\O2S\\Engine\\core\\src\\main\\resources\\o2s\\script\\retreivalUtil.js"));
            Invocable invocable = (Invocable) nashornEngine;
            Object configJson = invocable.invokeFunction("typeRetreivalConfig", false);

            var typeToken = new TypeToken<List<Map<String, Object>>>(){}.getType();
            config = new Gson().fromJson((String)configJson, typeToken);
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return config;
    }


    public String discoverTypeAndValidate(Connection conn, DeviceDto device){
        String validationResult = null;

        ScriptEngineFactory sef = new NashornScriptEngineFactory();
        ScriptEngine nashornEngine = sef.getScriptEngine();

        try {//TODO fileName as config
            nashornEngine.eval(new FileReader("D:\\DND\\VSCode\\O2S\\Engine\\core\\src\\main\\resources\\o2s\\script\\retreivalUtil.js"));
            Invocable invocable = (Invocable) nashornEngine;
            invocable.invokeFunction("discoverType", conn, device);
            if(device.getType() != null){
                invocable.invokeFunction("validateBasePath", conn, device);
                if(device.getBasePath() != null){
                    // copy script on target machine and execute to validate api access
                    var sourcePath = "D:/DND/VSCode/O2S/Engine/core/src/main/resources/o2s/script";
                    var fileName = "test";
                    var fileExtention = device.getType() == DeviceType.WINDOWS ? ".ps1" : ".sh";

                    validationResult = (String)invocable.invokeFunction("validateO2SAccess", conn, device, sourcePath, fileName+fileExtention);
                }
            }
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return validationResult;
    }

    // public static void main(String[] args) {
    //     System.out.println(new NHEngine().getTypeRetreivalConfig());
    // }
}
