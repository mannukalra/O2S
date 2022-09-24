package com.o2s.util;

import com.o2s.data.dto.DeviceDto;

public class PropsUtil {

    public static String replaceHostProps(String content, DeviceDto device){
        
        var props = device.getProps();
        for(var key : props.keySet()){
            if(content.contains("%"+key+"%")){
                content = content.replace("%"+key+"%", props.get(key));
            }
        }
        
        return content;
    }

    public static String replaceCommonProps(String content){
        //
        return content;
    }

    public static String replaceAllProps(String content, DeviceDto device){
        content = replaceHostProps(content, device);
        content = replaceCommonProps(content);
        return content;
    }
    
    
}
