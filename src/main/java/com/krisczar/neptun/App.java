package com.krisczar.neptun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String args[]){
        // TODO: LOAD ALL VARIABLES
        // TODO: MAKE API GIVING LATEST RESULTS

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
    }
}
