package com.krisczar.neptun.FCM;

import com.krisczar.neptun.RulesResolver.ResolverNew;
import com.krisczar.neptun.SupportServices.FilesIO;
import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.ConceptActivator;
import org.megadix.jfcm.FcmConnection;
import org.megadix.jfcm.conn.WeightedConnection;

import java.util.*;

public class FCMFileLoader {
    private CognitiveMap map = new CognitiveMap("FCM");
    private Map<String, Double> wagsMap = new HashMap<>();
    private Set<String> conceptsSet = new HashSet<>();
    private Set<String> line = new HashSet<>();

    private List<Concept> concepts = new ArrayList<>();

    public FCMFileLoader(ConceptActivator af) {
        // here map is creating
        loadAllFiles();
        loadWags();
        loadConcepts(af);
        loadConnections();
    }

    public CognitiveMap getMap() {
        return map;
    }

    private void loadWags(){
        final String fileWagsFile = "files/fcm-files/52.txt";

        List<String> wagsListTmp = FilesIO.loadAllLines(fileWagsFile);

        wagsListTmp.forEach(code -> {
            String[] wagsPair = code.split(";");
            wagsMap.put(wagsPair[0], Double.valueOf(wagsPair[1]));
        });
    }

    private void loadAllFiles(){
        line.addAll(FilesIO.loadAllLines("files/fcm-files/56.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/56_2.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/57.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/58.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/59.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/88.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/89.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/92.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/130.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/132.txt"));
    }

    private void loadConcepts(ConceptActivator af){
        line.forEach(conceptLine -> createConceptsByLine(conceptLine));

        conceptsSet.forEach(con -> finalCreationOfConcept(con, af));
    }

    private void createConceptsByLine(String lineFromFile){
        String[] extractedConceptNames = lineFromFile.split(":");
        String[] extractedSecountPartOfExtractedConceptNames = extractedConceptNames[1].split("="); //sorry, i didnt find better name
        String conceptName1 = extractedConceptNames[0];
        String conceptName2 = extractedSecountPartOfExtractedConceptNames[0];

//        System.out.println(conceptName1);
//        System.out.println(conceptName2);

        conceptsSet.add(conceptName1);
        conceptsSet.add(conceptName2);
    }

    private void finalCreationOfConcept(String conceptName, ConceptActivator af){
        Concept c1 = new Concept(conceptName, null, af, 0.0, resolveConceptOutput(conceptName), false);

        concepts.add(c1);

        map.addConcept(c1);
    }

    private double resolveConceptOutput(String conceptName){
        if (ResolverNew.isCodeInResolvedList(conceptName)){
            return 0.5;
        }

        else {
            return 0.0;
        }
    }

    private void loadConnections(){
        for (String lineFromFile : line) {
            createConnection(lineFromFile);
        }
    }

    private void createConnection(String lineFromFile){
        String[] extractedTmp1 = lineFromFile.split(":");
        String[] extractedTmp2 = extractedTmp1[1].split("=");

        String concept1 = extractedTmp1[0];
        String concept2 = extractedTmp2[0];

        double weight;
        try{
            weight = wagsMap.get(extractedTmp2[1]);
        }
        catch (NullPointerException e){
            e.printStackTrace();
//            System.out.println("Creating connection for: " + concept1 + " -> " + concept2 + " " + " " + extractedTmp2[1]);
            weight = 0.0;
        }


        String connectionName = concept1 + " -> " + concept2;
        FcmConnection conn_1 = new WeightedConnection(connectionName, null, weight);


        map.addConnection(conn_1);
        map.connect(concept1, connectionName, concept2);
    }
}
