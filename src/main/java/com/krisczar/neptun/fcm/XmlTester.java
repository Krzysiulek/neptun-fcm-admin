package com.krisczar.neptun.fcm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlTester {
    public static void main(String args[]){
        XmlCreator xmlCreator = new XmlCreator();

        xmlCreator.mapCreator();

        List<String> tmp = new ArrayList<String>();
        tmp.add("a");
        tmp.add("a");
        tmp.add("a");
        tmp.add("b");
        tmp.add("b");
        tmp.add("c");
        tmp.add("d");

        Set<String> tmp2 = new HashSet<>();
        tmp.forEach(letter -> tmp2.add(letter));

        tmp2.forEach(letter -> System.out.println(letter));
    }
}
