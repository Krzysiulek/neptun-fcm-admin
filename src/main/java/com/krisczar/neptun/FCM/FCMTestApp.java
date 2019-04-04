package com.krisczar.neptun.FCM;

import com.krisczar.neptun.GUI.FCMActivatorMenu;
import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.RulesResolver.ResolverNew;
import com.krisczar.neptun.SupportServices.*;
import org.megadix.jfcm.act.HyperbolicTangentActivator;

public class FCMTestApp {

    public static void main(String args[]){
        ModelResolver.loadAllModels();

        ResolverNew resolverNew = new ResolverNew(870);
        resolverNew.resolveSection1();
        resolverNew.resolveSection2();
        resolverNew.resolveSection3();


        FCMCreator fcm = new FCMCreator();
        fcm.run();

        System.out.println(fcm.toString());

//        FCMFileLoader  fileLoader = new FCMFileLoader(new HyperbolicTangentActivator());
//        FilesIO.loadAllLines("files/fcm-files/52.txt");

        FCMActivatorMenu fcmActivatorMenu = new FCMActivatorMenu();
    }
}
