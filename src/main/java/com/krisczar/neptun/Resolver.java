package com.krisczar.neptun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Resolver {
    // TODO: LOAD ALL MODELS AND PRINT THEM NEXT TO CODES
    // TODO: IMPLEMENT BLOK 3
    private String groupId;
    private TestResult testResult; // from user
    private List<String> userQAs;

    List<Variable> currentVars;
    List<Variable> allVars;

    int userId = 292; // test

    public Resolver(int userId){
        this.userId = userId;
        this.userQAs = new ArrayList<String>();
        this.allVars = new ArrayList<Variable>();
        this.currentVars = new ArrayList<Variable>();
        loadAllQA(userId);
    }

    public void loadAllQA(int userId){
        final String URL = "https://neptun-fcm.herokuapp.com/admin/api/users/{id}/latest";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL, String.class, userId);

        Gson g = new Gson();
        testResult = g.fromJson(response, TestResult.class);

        String resultsStr = testResult.getBeforeAnswers() + testResult.getAfterAnswers();
//        System.out.println(resultsStr);

        convertUserQAs(resultsStr);
    }

    private void convertUserQAs(String QAcodes){
        // przerzucenie JSONA do wewnętrznej struktury
        String[] tmpTab = QAcodes.split("\\)\\(");

        for(String code : tmpTab){
            code = code.replaceAll("\\)", "");
            code = code.replaceAll("\\(", "");

            String[] codeTab = code.split(":");
            String questionCode = codeTab[0];
            String answersCode = codeTab[1];

            answersCode = answersCode.replaceAll("\\[", "");
            answersCode = answersCode.replaceAll("]", "");

            String[] answers = answersCode.split(",");

            for(String ansCode : answers){
                ansCode = ansCode.replaceAll(" ", "");
                String properCode = questionCode + ":" + ansCode;
//                System.out.println(properCode);
                userQAs.add(properCode);
            }
        }
    }

    private void loadVariablesFromFile(String fileName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = file.readLine()) != null) {
            line = line.toUpperCase();

            String[] varSplitted = line.split("=");

            String answers = varSplitted[0];
            String name = varSplitted[1];

            allVars.add(new Variable(answers, name));
        }

        file.close();
    }

    public void resolveOneAnswer(String fileName){
        try {
            loadVariablesFromFile(fileName);

            List<Variable> tmpCurrentVars = new ArrayList<Variable>();

            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    tmpCurrentVars.add(var);
//                    userQAs.add(var.getName());
                    System.out.println("Vars num: " + tmpCurrentVars.size());
                }
            }

            if(tmpCurrentVars.size() == 0){
                return;
            }

            Map<String, Integer> counter = new HashMap<String, Integer>();

            // init
            for(Variable var : tmpCurrentVars){
                System.out.println("Name: " + var.getName());
                counter.put(var.getName(), 0);
            }

            // resolve
            for(Variable var : tmpCurrentVars){
                System.out.println("Name: " + var.getName());
                counter.put(var.getName(), counter.get(var.getName())+1);
            }

            int maxValueInMap = (Collections.max(counter.values()));  // This will return max value in the Hashmap


            for (Map.Entry<String, Integer> entry : counter.entrySet()) {  // Itrate through hashmap
                if (entry.getValue()==maxValueInMap) {
//                    System.out.println(entry.getKey());     // Print the key with max value
//                    userQAs.add(entry.getKey());
                    addToUserQAs(entry.getKey());

                    //adding this var to resolved vars
                    for(Variable var : tmpCurrentVars){
                        if (var.getName().equals(entry.getKey())){
                            currentVars.add(var);
                            break;
                        }
                    }

                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
//            resolveMoreAnswers(fileName);
        }
    }

    public void resolveMoreAnswers(String fileName){
        // LOAD FILES: 62, 11
        try {
            // BLOK 1
            loadVariablesFromFile(fileName);


            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
//                    userQAs.add(var.getName());
                    addToUserQAs(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());
        }
    }

    public void resolveSection_1(){
        userQAs.add("\n################## BLOK_1 ##################");
        // Stage 1
        userQAs.add("\n<<< STAGE_1 >>>");
        resolveOneAnswer("files/blok_1/17.txt");
        resolveMoreAnswers("files/blok_1/37.txt");
        resolveMoreAnswers("files/blok_1/38.txt");

        //Stage 2
        userQAs.add("\n<<< STAGE_2 >>>");
        resolveMoreAnswers("files/blok_1/65.txt");

        userQAs.add("\n<<< STAGE_3 >>>");
        resolveOneAnswer("files/blok_1/67.txt");
        resolveOneAnswer("files/blok_1/18.txt");

        userQAs.add("\n<<< STAGE_4 >>>");
        resolveOneAnswer("files/blok_1/60.txt");

        userQAs.add("\n<<< STAGE_5 >>>");
        resolveOneAnswer("files/blok_1/62.txt");
        resolveOneAnswer("files/blok_1/11.txt");
    }

    public void resolveSection_2(){
        userQAs.add("\n\n\n################## BLOK_2 ##################");
        // Stage 1
        userQAs.add("\n<<< STAGE_1 >>>");
        resolveMoreAnswers("files/blok_2/24.txt");
        resolveOneAnswer("files/blok_2/30.txt");

        resolveMoreAnswers("files/blok_2.1/20.txt");
        resolveMoreAnswers("files/blok_2.1/69.txt");
        resolveMoreAnswers("files/blok_2.1/81.txt");


        // Stage 2
        userQAs.add("\n<<< STAGE_2 >>>");
        resolveMoreAnswers("files/blok_2/23.txt");
        resolveMoreAnswers("files/blok_2/31.txt");

        // Stage 3
        userQAs.add("\n<<< STAGE_3 >>>");
        resolveMoreAnswers("files/blok_2/35.txt");

        // Stage 4
        userQAs.add("\n<<< STAGE_4 >>>");
        resolveMoreAnswers("files/blok_2/27.txt");

        // Stage 5
        userQAs.add("\n<<< STAGE_5 >>>");
        resolveMoreAnswers("files/blok_2/77.txt");
    }

    public void resolveSection_3(){
        // TODO: DO STH

        userQAs.add("\n\n\n################## BLOK_3 ##################");
        // Stage 1
        userQAs.add("\n<<< STAGE_1 >>>");
        resolveMoreAnswers("files/blok_3/42.txt");
        resolveMoreAnswers("files/blok_3/48.txt");
        resolveOneAnswer("files/blok_3/43.txt");
        resolveOneAnswer("files/blok_3/44.txt");

        // Stage 2
        userQAs.add("\n<<< STAGE_2 >>>");
        resolveMoreAnswers("files/blok_3/47.txt");
        resolveOneAnswer("files/blok_3/49.txt");
        resolveOneAnswer("files/blok_3/50.txt");
        resolveOneAnswer("files/blok_3/63.txt");
        resolveOneAnswer("files/blok_3/72.txt");
        // TODO: SUMY

        // Stage 3
        userQAs.add("\n<<< STAGE_3 >>>");
        resolveMoreAnswers("files/blok_3/51.txt");

        // Stage 4
        userQAs.add("\n<<< STAGE_4 >>>");
        makeSums();

        // Stage 5
        userQAs.add("\n<<< STAGE_5 >>>");
        resolveOneAnswer("files/blok_3/54.txt");


    }

    private void makeSums(){
        // COUNTING SZ
        int counterSZ = 0;
        for(String code : userQAs){
            if(Pattern.matches("SZ[0-9]+", code)){
//                System.out.println(code);
                counterSZ++;
            }

        }
        System.out.println("SZ = " + counterSZ);

        // CONVERTING SZ TO PSZ
        String namePSZ = "";
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader("files/models/83.txt"));
            String line;

            while ((line = file.readLine()) != null) {
                line = line.replaceAll(" ", "");
                line = line.replaceAll("\uFEFF", "");

                String[] tmp = line.split(";");
                String ans = tmp[0];
                String interval = tmp[1];

                String[] intervals = interval.split("-");
                int lowerInterval = Integer.parseInt(intervals[0]);
                int higherInterval = Integer.parseInt(intervals[1]);

                if(counterSZ >= lowerInterval && counterSZ <= higherInterval){
                    namePSZ = ans;
                    userQAs.add(namePSZ);
                    currentVars.add(new Variable("", namePSZ));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CONVERTING PSZ TO WSZ
        String nameWSZ = namePSZ.replace("PSZ", "WSZ");
        System.out.println("WSZ = " + nameWSZ);
        userQAs.add(nameWSZ);
        currentVars.add(new Variable("", nameWSZ));

        String nameWCWZ = getVarNameFromGroup("WCWZ");
        String nameWODS = getVarNameFromGroup("WODS");
        System.out.println("WODS"+nameWODS);
        System.out.println("WCWZ"+nameWCWZ);

//        ModelResolver.loadAllModels();
//        System.out.println("DUPA:" + ModelResolver.getModel(nameWODS));
        double numbWSZ = Double.parseDouble(ModelResolver.getModel(nameWSZ));
        double numbWSWZ = Double.parseDouble(ModelResolver.getModel(nameWCWZ));
        double numbWODS = Double.parseDouble(ModelResolver.getModel(nameWODS));
        double sum = numbWODS + numbWSWZ + numbWSZ;
        userQAs.add("Suma WSZ, WSWZ, WODS = " + sum);

        // CONVERTING SUM TO ZZOE
        String nameZZOE = getZZOE(sum);
        System.out.println("ZZOE: " + nameZZOE);
        userQAs.add(nameZZOE);
        currentVars.add(new Variable("", nameZZOE));

    }

    private String getZZOE(double sum){
        String nameZZOE = "";
        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader("files/models/53.txt"));
            String line;

            while ((line = file.readLine()) != null) {
                line = line.replaceAll(" ", "");
                line = line.replaceAll("\uFEFF", "");

                String[] tmp = line.split(";");
                String ans = tmp[0];
                String interval = tmp[1];

                String[] intervals = interval.split("-");
                double lowerInterval = Double.parseDouble(intervals[0]);
                double higherInterval = Double.parseDouble(intervals[1]);

                if(sum >= lowerInterval && sum <= higherInterval){
                    nameZZOE = ans;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameZZOE;
    }

    private void addToUserQAs(String code){
        String[] codes = code.split(";");
        for(String tmp : codes)
            userQAs.add(tmp);
    }

    public void printAllQAs(){
        System.out.println("\n\nWYNIKI");
        for(String qas : userQAs){
            System.out.println(qas);
        }
    }

    public static String getUsersIDs(){
        final String URL = "https://neptun-fcm.herokuapp.com/admin/api/users/id/all";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL, String.class);

        Gson g = new Gson();
        Map<String, Integer> son = new Gson().fromJson(response, HashMap.class);

        System.out.println(son.get("test"));

        System.out.println(response);
//        System.out.println(resultsStr);

        return response;
    }

    public String getAllQAs(){
        String tmp = "";

        for (String qa:
             userQAs) {
            tmp += qa + ": " + ModelResolver.getModel(qa) + "\n\n";
        }

        return tmp;
    }

    private String getVarNameFromGroup(String group){
        for(Variable tmp : currentVars){
            if (tmp.getGroup().equals(group))
                return tmp.getName();
        }

        for(Variable tmp : allVars){
            if (tmp.getGroup().equals(group))
                return tmp.getName();
        }
        return "varNotFoundByGroup";
    }

    public void printWWT(){
        int i = 0;
        for (Variable var :
                currentVars) {
            if (var.getGroup().equals("WT"))
                i++;
        }
        System.out.println("ILE I: " + i);
    }
}
