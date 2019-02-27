package com.krisczar.neptun;

import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Resolver {
    private String groupId;
    private TestResult testResult; // from user
    private List<String> userQAs;

    // TODO: LOAD ALL VARS
    List<Variable> currentVars;
    List<Variable> allVars;

    int userId = 513; // krzysioAdmin
//    int userId = 552; // krzysioAdmin

    public Resolver(){
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
        System.out.println(resultsStr);

        convertUserQAs(resultsStr);
    }

    private void convertUserQAs(String QAcodes){
        // przerzucenie JSONA do wewnętrznej struktury
        String[] tmpTab = QAcodes.split("\\)\\(");

        // TODO: TUTAJ TRZEBA ZROBIC TAK, ŻEBY PYTANIE PQ1:A1,A2 DOSTALO PQ1:A1 PQ1:A2
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
                System.out.println(properCode);
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

    public void resolveStage_1(){
        //TODO: LOAD FILES: 17, 37, 38
        try {
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/17.txt");
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/37.txt");
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/38.txt");

            for(Variable var : allVars){
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
                    userQAs.add(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resolveStage_2() {
        //TODO: LOAD FILES: 65
        try {
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/65.txt");

            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
                    userQAs.add(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resolveStage_3(){
        //TODO: LOAD FILES: 18, 67
        try {
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/18.txt");
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/67.txt");

            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
                    userQAs.add(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resolveStage_4(){
        //TODO: LOAD FILES: 60
        try {
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/60.txt");

            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
                    userQAs.add(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resolveStage_5(){
        //TODO: LOAD FILES: 62, 11
        try {
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/62.txt");
            loadVariablesFromFile("/Users/krzysztofczarnecki/Documents/GitHub/neptun-fcm-admin/files/11.txt");

            for (Variable var : allVars) {
                if (!var.value && var.checkMe(userQAs)) {
                    System.out.println("Resolved " + var.getName());
                    currentVars.add(var);
                    userQAs.add(var.getName());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
