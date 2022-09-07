package com.o2s.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    
    public static String findMatch(String target, String regex, int returnGroup){
        String result = null;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        if(matcher.find()) {
            result = matcher.group(returnGroup);
        }
        return result;
    }


}
