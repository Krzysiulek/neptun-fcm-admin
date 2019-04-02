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

        runner = new SimpleFcmRunner(map, 0.01, 10000000);
        // -0.4331887586753665	-0.947737112257481	0.8751815684966339	0.9336449855686867
        // -0.9467608581847212	-0.9046603396242264	-0.9429504195393419	-0.34955535392491843

        runner.run();
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
