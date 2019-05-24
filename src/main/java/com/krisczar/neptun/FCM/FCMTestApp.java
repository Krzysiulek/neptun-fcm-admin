package com.krisczar.neptun.FCM;

import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.GUI.ActivatorOptions;
import com.krisczar.neptun.RulesResolver.ResolverNew;

public class FCMTestApp {

    public static void main(String args[]){
        ModelResolver.loadAllModels();

        ResolverNew resolverNew = new ResolverNew(870);
        resolverNew.resolveSection1();
        resolverNew.resolveSection2();
        resolverNew.resolveSection3();
//
//
//        FCMCreator fcm = new FCMCreator();
//        fcm.run();
//
//        System.out.println(fcm.toString());

//        FCMFileLoader  fileLoader = new FCMFileLoader(new HyperbolicTangentActivator());
//        FilesIO.loadAllLines("files/fcm-files/52.txt");

//        FCMActivatorMenu fcmActivatorMenu = new FCMActivatorMenu();

        ActivatorOptions activatorOptions = new ActivatorOptions("Linear Activator");
    }
}
