//package com.krisczar.neptun;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserQuestionAnswers {
//    private String questionCode;
//    private List<String> answerCodes;
//
//    public UserQuestionAnswers(String code){
//        answerCodes = new ArrayList<String>();
//
//        code = code.replaceAll("\\)", "");
//        code = code.replaceAll("\\(", "");
//
//
//        String[] codeTab = code.split(":");
//        questionCode = codeTab[0];
//
//        codeTab[1] = codeTab[1].replaceAll("\\[", "");
//        codeTab[1] = codeTab[1].replaceAll("]", "");
////        System.out.println("<<<<");
////        System.out.println(codeTab[0] + " CDT1: " + codeTab[1]);
//
//
//        String[] answers = codeTab[1].split(",");
//
//
//        for(String ans : answers){
////            System.out.print(questionCode + " Ans: ");
//            ans = ans.replaceAll(" ", "");
////            tmpAns = tmpAns.replaceAll("...", "");
////            System.out.println(ans);
//            answerCodes.add(ans);
//        }
//
////        System.out.println(">>>>>");
//    }
//
//    public String getQuestionCode() {
//        return questionCode;
//    }
//
//    public List<String> getAnswerCodes() {
//        return answerCodes;
//    }
//}
