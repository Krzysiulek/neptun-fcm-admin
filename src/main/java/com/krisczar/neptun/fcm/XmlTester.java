package com.krisczar.neptun.fcm;

import java.util.*;

public class XmlTester {
    public static void main(String args[]){
        Set<String> s = new LinkedHashSet<>();
        s.add("c");
        s.add("c");
        s.add("d");
        s.add("a");
        s.add("c");
        s.add("z");

        s.forEach(code -> System.out.println(code));


        XmlCreator xmlCreator = new XmlCreator();

        xmlCreator.mapCreator();


    }
}
