package com.krisczar.neptun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*


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
        setGroup(name);
        this.qAsTab = questionsAnswers.split(";");
    }

    public boolean checkMe(List<String> userQA){
        // pamietac o tym, ze jezeli wystepują plusy, to ma inaczej się zachowywać
        for(String qa: qAsTab){
//            System.out.println(qa);

            // >>>>>>>>>>>>>>>DLA REGUL WIELOKROTNEGO WYBORU>>>>>>>>>>>>>>>>>>>>>>>>
            if(qa.contains("+")){
                System.out.print("REGULA Z PLUSEM    :");
                System.out.println(qa);

//                Map<String, Integer> repeatsMap = new HashMap<String, Integer>();
                String[] separateAns = qa.split("\\+");
//                System.out.println("QA: " + qa);

                int counter = 0;
                for (String ans : separateAns) {
                    if (isCodeInList(userQA, ans)){
                        if(!name.contains(">")){
                            setName(name);
                            break;
                        }
                        else {
                            String[] tmp = name.split(">");
                            setName(tmp[counter]);
                            break;
                        }
                    }
                    counter++;
                }
            }
            // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            else if(qa.contains("|")){
                String[] separateAns = qa.split("\\|");
//                System.out.println("QA: " + qa);

                for (String ans : separateAns) {
                    if (isCodeInList(userQA, ans)){
                        setName(name);
                        break;
                    }
                }
            }


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

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getQuestionsAnswers() {
        return questionsAnswers;
    }

    private void setName(String name){
        this.name = name;
        setGroup(name);
    }

    private void setGroup(String name){
        this.group = name.replaceAll("[0-9]", "");
    }

    public void printAll(){
        System.out.println(this.getQuestionsAnswers());
        System.out.println(this.getName());
        System.out.println(this.group);
        System.out.println(this.value);
        System.out.println();
    }
}

