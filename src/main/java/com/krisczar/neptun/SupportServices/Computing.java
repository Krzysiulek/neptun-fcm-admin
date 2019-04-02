package com.krisczar.neptun.SupportServices;

public class Computing {
    public static boolean isInRange(double num, double r1, double r2){
        if ( num >= r1 && num <= r2){
            return true;
        }
        else
            return false;
    }

    public static int roundTo5(int liczba){

        int num = (int) (5*(Math.floor(Math.abs(liczba/5))));

        if(liczba%5==0)
        {
            return liczba-5;
        }

        else{
            return num;
        }

    }


}
