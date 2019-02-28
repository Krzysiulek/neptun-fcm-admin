package com.krisczar.neptun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String args[]){
        // TODO: MAKE API SHOWING ALL USERS
        // TODO: MAKE GUI


//        Map<String, Integer> counter = new HashMap<String, Integer>();
//        counter.put("dupa", 0);
//        counter.put("dupa", counter.get("dupa")+1);
//        counter.put("dupa", counter.get("dupa")+1);
//        counter.put("dupa", counter.get("dupa")+1);
//        System.out.println(counter.get("dupa"));

        Resolver res = new Resolver();

        System.out.println("\n>>>>>>>>>>>>>>>>");
        System.out.println("Stage 1\n");
        res.resolveStage_1();

        System.out.println("\n>>>>>>>>>>>>>>>>");
        System.out.println("Stage 2\n");
        res.resolveStage_2();

        System.out.println("\n>>>>>>>>>>>>>>>>");
        System.out.println("Stage 3\n");
        res.resolveStage_3();

        System.out.println("\n>>>>>>>>>>>>>>>>");
        System.out.println("Stage 4\n");
        res.resolveStage_4();

        System.out.println("\n>>>>>>>>>>>>>>>>");
        System.out.println("Stage 5\n");
        res.resolveStage_5();

        res.printAllQAs();
    }
}
