package com.patterns.proxy;

import java.util.*;

interface Tuple<String , Integer, Map>{
    java.lang.String get ();
}

public class Generic {


    public static void main(String[] args) {
        Map <String, String> map = new TreeMap();

        map.put("key","val");

        Inter<Map> inter = new Inter(){
            @Override
            public Object Instance() {
                return map;
            }
        };

        SomeGeneric<Map<String,String>> s = new SomeGeneric(map);
        System.out.println(s.getT().values());

        System.out.println(inter.Instance());
    }
}
