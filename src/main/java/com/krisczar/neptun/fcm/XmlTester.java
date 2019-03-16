package com.krisczar.neptun.fcm;

public class XmlTester {
    public static void main(String args[]){
        XmlCreator xmlCreator = new XmlCreator();

        xmlCreator.mapCreator();
        xmlCreator.saveMapToFile();
    }
}
