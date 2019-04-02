package com.krisczar.neptun.FCM;

import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.ConceptActivator;
import org.megadix.jfcm.FcmConnection;
import org.megadix.jfcm.act.HyperbolicTangentActivator;
import org.megadix.jfcm.conn.WeightedConnection;
import org.megadix.jfcm.utils.FcmRunner;
import org.megadix.jfcm.utils.SimpleFcmRunner;

public class FCMCreator {
    CognitiveMap map;
    FcmRunner runner;

    public FCMCreator() {
        mapCreator();

        runner = new SimpleFcmRunner(map, 0.01, 10000);
        // -0.6874481398241583	-0.9531267767773045	0.7506556285495118	0.9310642751353773 - runner
        // -0.6874481398241583	-0.9531267767773045	0.7506556285495118	0.9310642751353773 - runner.coverage()
        // 0.08975778474716012	0.09966799462495582	0.5370495669980353	0.39693043200507755 - map execute
        // -0.011857518357420148	-0.9275528946673234	0.9330843864322214	0.9345998599282629 - map execute + runner


//        runner.run();
        runner.converge();
//        map.execute();

        ExampleUtils.printMapState(map);


    }

    private void mapCreator(){
        map = new CognitiveMap("FCM");
        ConceptActivator af = new HyperbolicTangentActivator();

        // TODO: CHOICE OF ACTIVATOR

        // load rules
        // make concepts
        // make connections
        // show results
        makeConcepts(af);
        makeConnections();


    }

    private void makeConcepts(ConceptActivator af){
        Concept c1 = new Concept("Interest rate", null, af, 0.0, 0.1, false);
        map.addConcept(c1);

        Concept c2 = new Concept("Productive investments", null, af, 0.0, 0.5, false);
        map.addConcept(c2);

        Concept c3 = new Concept("Occupation", null, af, 0.0, 0.1, false);
        map.addConcept(c3);

        Concept c4 = new Concept("Inflation", null, af, 0.0, 0.0, false);
        map.addConcept(c4);
    }

    private void makeConnections(){
        FcmConnection conn_1 = new WeightedConnection("Interest rate -> Productive investments", null, -0.8);
        map.addConnection(conn_1);
        FcmConnection conn_2 = new WeightedConnection("Productive investments -> Occupation", null, 1.0);
        map.addConnection(conn_2);
        FcmConnection conn_3 = new WeightedConnection("Occupation -> Inflation", null, 0.9);
        map.addConnection(conn_3);
        FcmConnection conn_4 = new WeightedConnection("Inflation -> Interest rate", null, 1.0);
        map.addConnection(conn_4);

        map.connect("Interest rate", "Interest rate -> Productive investments", "Productive investments");
        map.connect("Productive investments", "Productive investments -> Occupation", "Occupation");
        map.connect("Occupation", "Occupation -> Inflation", "Inflation");
        map.connect("Inflation", "Inflation -> Interest rate", "Interest rate");
    }


}
