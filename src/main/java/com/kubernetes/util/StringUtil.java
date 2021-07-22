package com.kubernetes.util;

import java.util.*;

public class StringUtil {
    public static String getString(Map<String,String> params){
        StringBuilder result= new StringBuilder();
        for(String key:params.keySet()){
            result.append(key).append(":").append(params.get(key)).append(",");
        }
        return result.toString();
    }
}
