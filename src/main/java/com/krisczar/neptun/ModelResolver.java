package com.krisczar.neptun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class ModelResolver {
    private static Map<String, String> models = new HashMap<String, String>();

    public static void loadAllModels(){
        try {
            loadVariablesFromFile("files/models/1.txt");
            loadVariablesFromFile("files/models/2.txt");
            loadVariablesFromFile("files/models/4.txt");
            loadVariablesFromFile("files/models/5.txt");
            loadVariablesFromFile("files/models/6.txt");
            loadVariablesFromFile("files/models/7.txt");
            loadVariablesFromFile("files/models/8.txt");
            loadVariablesFromFile("files/models/10.txt");
            loadVariablesFromFile("files/models/12.txt");
            loadVariablesFromFile("files/models/13.txt");
            loadVariablesFromFile("files/models/14.txt");
            loadVariablesFromFile("files/models/15.txt");
            loadVariablesFromFile("files/models/16.txt");
            loadVariablesFromFile("files/models/19.txt");
            loadVariablesFromFile("files/models/21.txt");
            loadVariablesFromFile("files/models/22.txt");
            loadVariablesFromFile("files/models/25.txt");
            loadVariablesFromFile("files/models/26.txt");
            loadVariablesFromFile("files/models/28.txt");
            loadVariablesFromFile("files/models/29.txt");
            loadVariablesFromFile("files/models/33.txt");
            loadVariablesFromFile("files/models/39.txt");
            loadVariablesFromFile("files/models/41.txt");
            loadVariablesFromFile("files/models/45.txt");
            loadVariablesFromFile("files/models/46.txt");
            loadVariablesFromFile("files/models/53.txt");
            loadVariablesFromFile("files/models/55.txt");
            loadVariablesFromFile("files/models/61.txt");
            loadVariablesFromFile("files/models/64.txt");
            loadVariablesFromFile("files/models/70.txt");
            loadVariablesFromFile("files/models/71.txt");
            loadVariablesFromFile("files/models/75.txt");
            loadVariablesFromFile("files/models/76.txt");
            loadVariablesFromFile("files/models/78.txt");
            loadVariablesFromFile("files/models/78_1.txt");
            loadVariablesFromFile("files/models/79.txt");
            loadVariablesFromFile("files/models/80.txt");
            loadVariablesFromFile("files/models/82.txt");
            loadVariablesFromFile("files/models/83.txt");
            loadVariablesFromFile("files/models/84.txt");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void loadVariablesFromFile(String fileName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = file.readLine()) != null) {
            line = line.replaceAll("\uFEFF", "");
            line = line.replaceAll(" ", "");
            System.out.println(line);
//            System.out.println(line);
//            line = line.toUpperCase();

            String[] varSplitted = line.split(";");

            String code = varSplitted[0];
            String model = varSplitted[1];

            models.put(code, model);
        }

        file.close();
    }

    public static String getModel(String code){
        try{
            String tmp = models.get(code);

            if(tmp == null){
                return "";
            }
            else{
                tmp = tmp.replaceAll("_", " ");
                String value = new String(tmp.getBytes("UTF-8"));
                return value;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static void printAll(){

    }

}
