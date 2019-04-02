package com.krisczar.neptun.RulesResolver;

import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.SupportServices.Computing;

public class WeirdVariables {

    public static String getPSZ(int howManySZ){
        if  (Computing.isInRange(howManySZ, 0, 3))
            return "PSZ5";
        else if (Computing.isInRange(howManySZ, 4,6))
            return "PSZ4";
        else if (Computing.isInRange(howManySZ, 7,9))
            return "PSZ3";
        else if (Computing.isInRange(howManySZ, 10,12))
            return "PSZ2";
        else if (Computing.isInRange(howManySZ, 13,69))
            return "PSZ1";


        return "";
    }

//    ZZOE1;4.2-5.0
//    ZZOE2;3.4-4.19
//    ZZOE3;2.6-3.39
//    ZZOE4;1.8-2.59
//    ZZOE5;1.0-1.79

    public static String getZZOE(double sum){
        if  (Computing.isInRange(sum, 4.2, 5.0))
            return "ZZOE1";
        else if (Computing.isInRange(sum, 3.4,4.19))
            return "ZZOE2";
        else if (Computing.isInRange(sum, 2.6,3.39))
            return "ZZOE3";
        else if (Computing.isInRange(sum, 1.8,2.59))
            return "ZZOE4";
        else if (Computing.isInRange(sum, 1.0,1.79))
            return "ZZOE5";


        return "";
    }


    public static double getValueOfCode(String code){
        String tmp = ModelResolver.getModel(code);

        System.out.println("################################################");
        System.out.println(code);
        System.out.println(tmp);
        System.out.println("");

        return Double.parseDouble(tmp);
    }

}
