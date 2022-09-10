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

public class NHEngine {

    public static List<Map<String, Object>> getOsRetreivalConfig(){
        List<Map<String, Object>> config = null;

        ScriptEngineFactory sef = new NashornScriptEngineFactory();
        ScriptEngine nashornEngine = sef.getScriptEngine();

        try {//TODO fileName as config
            nashornEngine.eval(new FileReader("D:\\DND\\VSCode\\O2S\\Engine\\core\\src\\main\\resources\\o2s\\script\\retreivalUtil.js"));
            Invocable invocable = (Invocable) nashornEngine;
            Object configJson = invocable.invokeFunction("osRetreivalConfig");

            var typeToken = new TypeToken<List<Map<String, Object>>>(){}.getType();
            config = new Gson().fromJson((String)configJson, typeToken);
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return config;
    }

    public static void main(String[] args) {
        // ScriptEngineFactory sef = new NashornScriptEngineFactory();
        // ScriptEngine nashornEngine = sef.getScriptEngine();

        // DeviceDto deviceDto = new DeviceDto();
        // deviceDto.setAlias("test alias");
        // deviceDto.setHost("test host!");
        // try {
        //     nashornEngine.eval(new FileReader("D:\\DND\\VSCode\\O2S\\Engine\\core\\src\\main\\resources\\o2s\\script\\nh.js"));
        //     Invocable invocable = (Invocable) nashornEngine;
        //     Object funcResult = invocable.invokeFunction("eval", deviceDto); 
        //     System.out.println(funcResult);
        // } catch (ScriptException | NoSuchMethodException | FileNotFoundException e) {
        //     e.printStackTrace();
        // }



        // try{
        //     Bindings bindings = nashornEngine.createBindings();
        //     bindings.put("count", 3);
        //     bindings.put("name", "baeldung");    
        //     String script = "var greeting='Hello ';" +
        //         "for(var i=count;i>0;i--) { " +
        //             "greeting+=name + ' '" +
        //         "}" + "greeting";
            
        //     Object bindingsResult = nashornEngine.eval(script, bindings);
        //     System.out.println(bindingsResult);
        // } catch (ScriptException e) {
        //     e.printStackTrace();
        // }

        System.out.println(getOsRetreivalConfig());
    }
}
