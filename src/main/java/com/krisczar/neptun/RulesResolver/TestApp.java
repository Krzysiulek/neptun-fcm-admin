package com.krisczar.neptun.RulesResolver;


public class TestApp {
    public static void main(String arg[]){

//        przyblizDo5(1);


//        ModelResolver.loadAllModels();
//
//        ResolverNew resolverNew = new ResolverNew(870);
//
//        resolverNew.resolveSection1();
//        resolverNew.resolveSection2();
//        resolverNew.resolveSection3();
//
//        resolverNew.printAllResolvedVars();
    }


    public static void przyblizDo5(int liczba){

        int num = (int) (5*(Math.floor(Math.abs(liczba/5))));

        if(liczba%5==0)
            System.out.println(liczba - 5);

        else
            System.out.println(num);
    }
}
