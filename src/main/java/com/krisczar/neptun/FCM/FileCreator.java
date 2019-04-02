package com.krisczar.neptun.FCM;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileCreator {
    public static void main(String args[]){
        changeFile();
    }


    public static void changeFile(){
        List<String> newLine = new ArrayList<>();

        // pass the path to the file as a parameter
        File file =
                new File("files/FCM-files/56.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()){
            String curLine = sc.nextLine();
            String line[] = curLine.split(":");
            String line2[] = line[1].split("=");

            String WT = line[0];
            String SZ = line2[0];
            String WAG = line2[1];

            newLine.add(SZ + ":" + WT + "=" + WAG + "\n");
        }

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("files/FCM-files/56_2.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String tmp :
                newLine) {
            try {
                writer.write(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

