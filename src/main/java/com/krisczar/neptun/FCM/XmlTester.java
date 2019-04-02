package com.krisczar.neptun.FCM;

import com.krisczar.neptun.RulesResolver.ResolverNew;
import com.krisczar.neptun.ModelResolver;

public class XmlTester {
    public static void main(String args[]){
        ModelResolver.loadAllModels();

        ResolverNew resolverNew = new ResolverNew(870);
        resolverNew.resolveSection1();
        resolverNew.resolveSection2();
        resolverNew.resolveSection3();

        XmlCreator xmlCreator = new XmlCreator();

        xmlCreator.mapCreator();


    }
}
