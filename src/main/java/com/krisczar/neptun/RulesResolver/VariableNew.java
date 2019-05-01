package com.krisczar.neptun.RulesResolver;

import java.util.List;

public class VariableNew {
    String line = null;
    String left = null;
    String right = null;

    String name = null;
    boolean value = false;
    int section = 0;
    int stage = 0;

    public VariableNew(String name, boolean value, int section, int stage){
        this.name = name;
        this.value = value;
        this.section = section;
        this.stage = stage;
    }

    public VariableNew(String line){
        String[] lineParts = line.split("=");

        this.line = line;
        left = lineParts[0];
        right = lineParts[1];
    }


    public boolean checkMe(List<VariableNew> resolvedVars){
        // R1=IR3;RC3;SA1;PR3;PH7
//        System.out.println(left);
//        System.out.println(right);
//        System.out.println();

        return checkAllPatterns(resolvedVars);
    }


    private boolean checkAllPatterns(List<VariableNew> resolvedVars){
        // #1 s1
        final String pattern17 = "^((Q\\d+|PQ\\d+):(A\\d+|PA\\d+);)+((Q\\d+|PQ\\d+):(A\\d+|PA\\d+))+=\\w+$"; // files: 17
        final String pattern37 = "^(Q\\d+|PQ\\d+):(A\\d+|PA\\d+)=T\\d+$";
        final String pattern38 = "^(Q\\d+|PQ\\d+):(A\\d+|PA\\d+)=RR\\d+$";

        // #1 s2
        final String pattern65 = "^(\\w+);T\\d+;((Q\\d+|PQ\\d+):(A\\d+|PA\\d+))+;(Q\\d+|PQ\\d+):(A\\d+|PA\\d+)=\\w+$";

        // #1 s3
        final String pattern67 = "^DRUIES\\d+=\\w+$";
        final String pattern18 = "^DRUIES\\d+=\\w+$";

        // #1 s4
        final String pattern60 = "^T\\d+;R\\d+;K\\d+;(RR\\d+\\+)*RR\\d+=(R\\d+>*)+$";

        // #1 s5
        final String pattern62 = "^R\\d+=\\w+$";
        final String pattern11 = "^R\\d+=IR\\d+;RC\\d+;SA\\d+;PR\\d+;PH\\d+";

        // #2 s1
        final String pattern30 = "^Q\\d+:A\\d+=ZZE\\d+$";
        final String pattern24 = "^Q\\d+:A\\d+=ZD\\d+$";

        // #2 s2
        final String pattern23 = "^ZD\\d+=(PCT\\d+;)*(PCT\\d+)$";
        final String pattern31 = "^ZZE\\d++=(ZE\\d+;)*(ZE\\d+)$";

        // #2 s3
        final String pattern101_125 = "^Q16:A\\d+;PCT\\d+=(WWT\\d+;)*(WWT\\d+)$";

        final String pattern27 = "^(Q\\d+:A\\d+|WWT\\d+|ZE\\d+)=ZG\\d+(;WWT\\d+)*$";


        // #3 s1
        final String pattern42 = "^Q\\d+:A\\d+=SZ\\d+$";
        final String pattern43 = "^Q\\d+:A\\d+=ODS\\d+$";
        final String pattern44 = "^Q\\d+:A\\d+=CWZ\\d+$";
        final String pattern48 = "^Q\\d+:A\\d+=ISZ\\d+$";

        // #3 s2
        final String pattern130 = "^PSZ\\d+=WSZ\\d+$";
        final String pattern72 = "^CWZ\\d+=WCWZ\\d+$";
        final String pattern50 = "^CWZ\\d+=ISZ\\d+$";
        final String pattern47 = "^SZ\\d+=ISZ\\d+$";
        final String pattern63 = "^ODS\\d+=WODS\\d+$";
        final String pattern49 = "^ODS\\d+=ISZ\\d+$";
        final String pattern131 = "^((PQ\\d+|Q\\d+):(PA\\d+|A\\d+)|(CST|CPD|CMB))=OP\\d+$";

        // #3 s3
        final String pattern51 = "^(ISZ\\d+\\+)*(ISZ\\d+)=BISZ$";

        // #3 s4
        final String pattern54 = "^Q\\d+:A\\d+;ZZOE\\d+=EEI\\d+$";


        // STAGE 1
        if (line.matches(pattern17)){
            return checkPatternQA(resolvedVars);
        }

        else if (line.matches(pattern37)){
            return checkPatternQA(resolvedVars);
        }

        else if (line.matches(pattern38)){
            return checkPatternQA(resolvedVars);
        }

        // STAGE 2
        else if (line.matches(pattern65)){
            return checkPatternQA(resolvedVars);
        }

        // STAGE 3
        else if (line.matches(pattern18)){
            return checkPatterDruies(resolvedVars);
        }

        else if (line.matches(pattern67)){
            return checkPatterDruies(resolvedVars);
        }

        // STAGE 4
        else if (line.matches(pattern60)){
            return checkPatternRR(resolvedVars);
        }

        // STAGE 5
        else if (line.matches(pattern62)){
            return checkPatterDruies(resolvedVars);
        }
        else if (line.matches(pattern11)){
            return checkPatterDruies(resolvedVars);
        }

        // BLOK 2
        // stage 1
        else if (line.matches(pattern30)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern24)){
            return checkPatternQA(resolvedVars);
        }

        //stage 2
        else if (line.matches(pattern23)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern31)){
            return checkPatternQA(resolvedVars);
        }

        // stage 3
        else if (line.matches(pattern101_125)){
            return checkPatternQA(resolvedVars);
        }

        // stage 4
        else if (line.matches(pattern27)){
            return checkPatterDruies(resolvedVars);
        }

        // SECTION 3
        // stage 1
        else if (line.matches(pattern42))
        {
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern43))
        {
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern44))
        {
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern48))
        {
            return checkPatternQA(resolvedVars);
        }

        // stage 2
        else if (line.matches(pattern130)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern72)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern50)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern47)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern63)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern49)){
            return checkPatternQA(resolvedVars);
        }
        else if(line.matches(pattern51)){
            return checkPatternBISZ(resolvedVars);
        }

        else if (line.matches(pattern54)){
            return checkPatternQA(resolvedVars);
        }
        else if (line.matches(pattern131)){
            return checkPatternQA(resolvedVars);
        }



        return false;
    }

    private boolean checkPatternBISZ(List<VariableNew> resolvedVars){
        String[] toResolve = left.split("\\+");

        for (String res :
                toResolve) {
            System.out.println("RES:   " + res);
            if (isCodeInResolvedVars(res, resolvedVars)){
                name = right;
                return true;
            }

        }

        return false;
    }

    private boolean checkPatternQA(List<VariableNew> resolvedVars){
        String[] toResolveCodes = left.split(";");

        for (String code : toResolveCodes){
            if (!isCodeInResolvedVars(code, resolvedVars)){
                return false;
            }
        }

        name = right;
//        System.out.println("Resolved: " + name);
        return true;
    }

    private boolean checkPatterDruies(List<VariableNew> resolvedVars){
        if (isCodeInResolvedVars(left, resolvedVars)){
            name = right;
            return true;
        }
        return false;
    }

    private boolean checkPatternRR(List<VariableNew> resolvedVars){
        String[] tmpCodes = left.split(";");

        for (String code : tmpCodes){
            if(code.matches("^(RR\\d+\\+*)+$")){
                // rozlozyc na czynniki pierwsze
                // jesli jakis nalezy -> wziąć odpowieni numer i wziąc go z lewej strony

                String[] tmpGT = code.split("\\+");

                int i = 0;
                for(String codeGT : tmpGT){
                    if (isCodeInResolvedVars(codeGT, resolvedVars)){
                        String[] tmpRightCodes = right.split(">");


                        name = tmpRightCodes[i];
                        return true;
                    }
                    i++;
                }

            }
            else {
                if (!isCodeInResolvedVars(code, resolvedVars)){
                    return false;
                }
            }

        }
        return false;
    }

    private boolean isCodeInResolvedVars(String code, List<VariableNew> resolvedVars){
        for (VariableNew var :
                resolvedVars) {
            if (code.equals(var.getName())){
                return true;
            }
        }

        return false;
    }

    public int getSection() {
        return section;
    }

    public int getStage() {
        return stage;
    }

    public String getName() {
        return name;
    }

    public boolean isValue() {
        return value;
    }
}
