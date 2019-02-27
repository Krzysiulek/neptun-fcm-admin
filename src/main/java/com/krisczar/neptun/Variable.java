package com.krisczar.neptun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    TODO: Zrobic tak, zeby poronwywało tylko i wyłącznie Stringi a nie jakieś bardziej zaawansowane struktury.

 */

public class Variable {
    private String questionsAnswers;
    private String[] qAsTab;

    private String name;
    private String group;
    public boolean value; // true jesli się zgadza


    public Variable(String questionsAnswers, String name){
        this.questionsAnswers = questionsAnswers.replaceAll("\uFEFF", "");
        this.name = name;
        this.group = name.replaceAll("\\[0-9]]", "");
        this.qAsTab = questionsAnswers.split(";");
    }


    public boolean checkMe(List<String> userQA){
        // TODO: checkMe
        // pamietac o tym, ze jezeli wystepują plusy, to ma inaczej się zachowywać
        for(String qa: qAsTab){
//            System.out.println(qa);

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            if(qa.contains("+")){
//                Map<String, Integer> repeatsMap = new HashMap<String, Integer>();
                String[] separateAns = qa.split("\\+");

                int counter = 0;
                for (String ans : separateAns) {
                    counter++;
                    if (isCodeInList(userQA, ans)){
                        String[] tmp = name.split(">");
                        setName(tmp[counter]);
                        break;
                    }
                }
            }

            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


            else {
                if(isCodeInList(userQA, qa)){
                    continue;
                }
                else{
                    value = false;
                    return false;
                }
            }
        }
        value = true;
        return true;
    }

    private boolean isCodeInList(List<String> userQA, String code){
        for(String uqa : userQA){
            if(uqa.equals(code))
                return true;
        }
        return false;
    }

    private int howManyCodeInList(List<String> userQA, String code){
        int counter = 0;
        for(String uqa : userQA){
            if(uqa.equals(code))
                counter++;
        }
        return counter;
    }

    public String getName() {
        return name;
    }

    public String getQuestionsAnswers() {
        return questionsAnswers;
    }

    private void setName(String name){
        this.name = name;
        setGroup(name);
    }

    private void setGroup(String name){
        this.group = name.replaceAll("\\[0-9]]", "");
    }

    public void printAll(){
        System.out.println(this.getQuestionsAnswers());
        System.out.println(this.getName());
        System.out.println(this.group);
        System.out.println(this.value);
        System.out.println();
    }
}

