package com.krisczar.neptun.SupportServices;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilesIO {

    public static void save(String newDirectoryName, String fileName, String fileExtension ,String toSave){
        makeDir(newDirectoryName);

        try {
            String pathWithDirecotry = newDirectoryName + "/" + fileName + "." + fileExtension;

            FileWriter fileWriter = new FileWriter(pathWithDirecotry);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(toSave);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDir(String directoryName){
        String[] directories = directoryName.split("/");
        String summaryPath = "";

        for(String dir : directories) {
            summaryPath += dir + "/";
            File directory = new File(summaryPath);
            if (!directory.exists()) {
                directory.mkdir();
            }
        }
    }

    public static List<String> loadAllLines(String fileName){
        List<String> fileLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = sCurrentLine.replaceAll("\uFEFF", "");
//                System.out.println(sCurrentLine);
                fileLines.add(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileLines;
    }
}
