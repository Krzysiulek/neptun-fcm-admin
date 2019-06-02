package com.krisczar.neptun.RulesResolver;

import com.google.gson.Gson;
import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.SupportServices.Computing;
import com.krisczar.neptun.SupportServices.FilesIO;
import com.krisczar.neptun.TestResult;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ResolverNew {
    static List<VariableNew> resolvedVariables;
    static long userId;

    public ResolverNew(int userId){
        resolvedVariables = new ArrayList<>();
        this.userId = userId;
        loadAllQA(userId);
    }

    public void resolveSection1(){
        final int section = 1;
        // stage 1
        resolveOneAnswer("files/blok_1/17.txt", section,1);
        resolveMoreAnswers("files/blok_1/37.txt",section,1);
        resolveMoreAnswers("files/blok_1/38.txt",section,1);

        // stage 2
        resolveMoreAnswers("files/blok_1/65.txt",section,2);

        // stage 3
        resolveOneAnswer("files/blok_1/18.txt",section,3);
        resolveOneAnswer("files/blok_1/67.txt",section,3);

        // stage 4
        resolveOneAnswer("files/blok_1/60.txt",section,4);

        // stage 5
        resolveOneAnswer("files/blok_1/62.txt",section,4);
        resolveOneAnswer("files/blok_1/11.txt",section,4);
    }

    public void resolveSection2(){
        final int section = 2;
        // stage 1
        resolveOneAnswer("files/blok_2/30.txt", section,1);
        resolveMoreAnswers("files/blok_2/24.txt", section,1);

        // stage 2
        resolveMoreAnswers("files/blok_2/23.txt", section,2);
        resolveMoreAnswers("files/blok_2/31.txt", section,2);

        // stage 3
        // 101 - 125

        for(int i = 101; i <= 125; i++){
            String filePath = "files/blok_2/" + i + ".txt";
            resolveOneOrMoreAnswers(filePath, section, 3);
        }

        // stage 4
        resolveMoreAnswers("files/blok_2/27.txt", section, 4);

    }

    public void resolveSection3(){
        // 42, 43, 44, 48
        final int section = 3;

        // stage 1
        resolveMoreAnswers("files/blok_3/42.txt", section, 1);
        resolveOneAnswer("files/blok_3/43.txt", section, 1);
        resolveOneAnswer("files/blok_3/44.txt", section, 1);
        resolveMoreAnswers("files/blok_3/48.txt", section, 1);

        // stage 2
        resolvePSZ(section, 2);
        resolveMoreAnswers("files/blok_3/130.txt", section, 2);
        resolveOneAnswer("files/blok_3/72.txt", section, 2);
        resolveOneAnswer("files/blok_3/50.txt", section, 2);
        resolveMoreAnswers("files/blok_3/47.txt", section, 2);
        resolveOneAnswer("files/blok_3/63.txt", section, 2);
        resolveOneAnswer("files/blok_3/49.txt", section, 2);
        resolveMoreAnswers("files/blok_3/131.txt", section, 2);

        // stage 3
        resolveMoreAnswers("files/blok_3/51.txt", section, 3);
        try{
            resolveSums(section, 3);
        }
        catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "User didn't finish test");
        }

        // stage 5
        resolveOneAnswer("files/blok_3/54.txt", section, 5);
    }

    private void resolvePSZ(int section, int stage){
        AtomicInteger counterSZ = new AtomicInteger();
        resolvedVariables.forEach(code -> {
            if (code.getName().matches("^SZ\\d+$"))
                counterSZ.getAndIncrement();
        });

        String PSZ = WeirdVariables.getPSZ(counterSZ.get());
        addToResolvedVariables(PSZ, section, stage);

    }

    private void resolveSums(int section, int stage){
        double sum = 0;

        String WSZ = getCode("WSZ");
        String WCWZ = getCode("WCWZ");
        String WODS = getCode("WODS");

        sum += WeirdVariables.getValueOfCode(WSZ);
        sum += WeirdVariables.getValueOfCode(WCWZ);
        sum += WeirdVariables.getValueOfCode(WODS);

        addToResolvedVariables("Suma WSZ, WCWZ, WODS: " + sum, section, stage);

        String ZZOE = WeirdVariables.getZZOE(sum);

        addToResolvedVariables(ZZOE, section, 4);
    }

    private void resolveMoreAnswers(String fileName, int section, int stage){
        List<VariableNew> varsFromFile = null;
        List<VariableNew> resolvedVarsFromFile = new ArrayList<>();

        try {
            varsFromFile = loadRulesFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (VariableNew var : varsFromFile){
            if(var.checkMe(resolvedVariables)){
//                System.out.println(var.getName());
                resolvedVarsFromFile.add(var);
            }
        }

        resolvedVarsFromFile.forEach(var -> addToResolvedVariables(var.getName(), section, stage));
    }

    private void resolveOneAnswer(String fileName,int section, int stage){
        List<VariableNew> varsFromFile = null;
        List<VariableNew> resolvedVarsFromFile = new ArrayList<>();
        Map<String, Integer> tmpCounterMap = new HashMap<>();

        try {
            varsFromFile = loadRulesFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (VariableNew var : varsFromFile){
            if(var.checkMe(resolvedVariables)){
//                System.out.println(var.getName());
                resolvedVarsFromFile.add(var);
            }
        }

        if(resolvedVarsFromFile.size() == 0)
            return;

        resolvedVarsFromFile.forEach(var -> tmpCounterMap.put(var.getName(), 0));

        resolvedVarsFromFile.forEach(var -> tmpCounterMap.put(var.getName(), tmpCounterMap.get(var.getName()) + 1));

        String key = Collections.max(tmpCounterMap.entrySet(), Map.Entry.comparingByValue()).getKey();

        addToResolvedVariables(key, section, stage);
    }

    private void resolveOneOrMoreAnswers(String fileName, int section, int stage){
        List<VariableNew> varsFromFile = null;
        List<VariableNew> resolvedVarsFromFile = new ArrayList<>();

        try {
            varsFromFile = loadRulesFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int counter = 0;
        for (VariableNew var : varsFromFile){
            if(var.checkMe(resolvedVariables)){
                resolvedVarsFromFile.add(var);

                if (counter == 0){
                    break;
                }
            }
            counter++;
        }

        if (counter == 0){
            resolvedVarsFromFile.forEach(var -> addToResolvedVariables(var.getName(), section, stage));
        }

        else{
            List<String> firstLineNotResVariables = Arrays.asList(varsFromFile.get(0).right.split(";"));
            System.out.println(firstLineNotResVariables.toString());

            List<String> differences = getDifferences(firstLineNotResVariables, resolvedVarsFromFile);

            System.out.println("Differences:");
            System.out.println(differences.toString());


            firstLineNotResVariables.forEach(var -> addToResolvedVariables(var, section, stage));
            differences.forEach(diff -> addToResolvedVariables(diff, section, stage));
        }
    }

    private void addToResolvedVariables(String var, int section, int stage){
        String[] tmpVars = var.split(";");

        for(String tmpVar : tmpVars){
            deleteIfExists(tmpVar);
            resolvedVariables.add(new VariableNew(tmpVar, true, section, stage));
        }
    }

    private void deleteIfExists(String code) {
        for (VariableNew var : resolvedVariables) {
            if (code.equals(var.getName())) {
                System.out.println("Uwaga usuwam " + code);
//                System.out.println(resolvedVariables.remove(var));
                resolvedVariables.remove(var);
                break;
            }
        }

        if(code.matches("^WWT\\d+$")){
            for (VariableNew var : resolvedVariables) {
                if(var.getName().matches("^WWT\\d+$")){

                    int varNumber = Integer.parseInt(var.getName().replace("WWT", ""));;
                    varNumber = Computing.roundTo5(varNumber);

                    int codeNumber = Integer.parseInt(code.replace("WWT", ""));;

                    if (codeNumber > varNumber + 1 && codeNumber <= varNumber + 5){
                        System.out.println("Var numb: " + varNumber);
                        System.out.println("Code numb: " + codeNumber);
                        System.out.println();

                        System.out.println("Removed becouse of WWT was in 1-5");
                        System.out.println("-" + var.getName());
                        System.out.println("+" + code);
                        System.out.println();

                        resolvedVariables.remove(var);
                        break;
                    }
                }
            }
        }
    }

    private List<String> getDifferences(List<String> firstLine, List<VariableNew> resolvedLines){
        List<String> diff = new ArrayList<>();

        for(String tmp : firstLine){
            if(!isCodeInList(tmp, resolvedLines)){
                diff.add(tmp);
            }
        }
        return diff;
    }

    private boolean isCodeInList(String code, List<VariableNew> codesList){
        for (VariableNew tmp : codesList){
            String[] codes = tmp.right.split(";");

            for (String cdV : codes){
                if (code.equals(cdV))
                    return true;
            }
        }
        return false;
    }

    public static boolean isCodeInResolvedList(String code){
        for (VariableNew tmp : resolvedVariables){
            if (tmp.name.equals(code))
                return true;
        }
        return false;
    }

    private List<VariableNew> loadRulesFile(String fileName) throws IOException {
        List<VariableNew> varsFromFile = new ArrayList<>();
        BufferedReader file;

        file = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = file.readLine()) != null) {
            line = line.replaceAll(" ", "");
            line = line.replaceAll("\uFEFF", "");

            varsFromFile.add(new VariableNew(line));
        }

        file.close();

        return varsFromFile;
    }

    private void loadAllQA(int userId){
        TestResult testResult; // from user

        final String URL = "https://neptun-FCM.herokuapp.com/admin/api/users/{id}/latest";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(URL, String.class, userId);

        Gson g = new Gson();
        testResult = g.fromJson(response, TestResult.class);

        String resultsStr = testResult.getBeforeAnswers() + testResult.getAfterAnswers();

        convertUserQAs(resultsStr);
    }

    private void convertUserQAs(String QAcodes){
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

                resolvedVariables.add(new VariableNew(properCode, true, 0, 0));
            }
        }
    }

    private String getCode(String pattern){
        for (VariableNew var : resolvedVariables){
            if (var.getName().contains(pattern))
                return var.getName();
        }
        return "";
    }

    public void saveToFile(){
        String directory = "results/rules/" + userId + "/";
        String fileName = String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd_hh_mm")));

        FilesIO.save(directory, fileName, "txt", toString());
    }

    public static List<VariableNew> getResolvedVariables() {
        return resolvedVariables;
    }

    public boolean isFCMneeded(){
        for (VariableNew var :
             resolvedVariables) {
            String varModel = ModelResolver.getModel(var.getName());
            if (varModel.contains("NIESPEĹ�NIONY WYMAGANY POZIOM") || varModel.contains("NIESPEŁNIONY WYMAGANY POZIOM ERGONOMICZNOŚCI")){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();

        for (VariableNew var :
                resolvedVariables) {

            toReturn.append("#").
                    append(var.getSection()).
                    append("-").
                    append(var.getStage()).
                    append(": ").
                    append(var.getName()).
                    append(": ").
                    append(ModelResolver.getModel(var.getName())).
                    append("\n");
        }
        return toReturn.toString();
    }

    public static long getUserId() {
        return userId;
    }
}
