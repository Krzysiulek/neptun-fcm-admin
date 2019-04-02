package com.krisczar.neptun.FCM;

import com.krisczar.neptun.RulesResolver.ResolverNew;
import com.krisczar.neptun.SupportServices.FilesIO;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.ConceptActivator;

import java.util.*;

public class FCMFileLoader {
    // TODO: LOAD ALL WAGS
    // TODO: LOAD ALL CONCEPTS
    // TODO: LOAD ALL CONNECTIONS
    Map<String, Double> wagsMap = new HashMap<>();
    List<Concept> concepts = new ArrayList<>();
    Set<String> connections = new HashSet<>();

    public FCMFileLoader(ConceptActivator af) {
        loadWags();
        loadConcepts(af);

    }

    private void loadWags(){
        final String fileWagsFile = "files/fcm-files/52.txt";

        List<String> wagsListTmp = FilesIO.loadAllLines(fileWagsFile);

        wagsListTmp.forEach(code -> {
            String[] wagsPair = code.split(";");
            wagsMap.put(wagsPair[0], Double.valueOf(wagsPair[1]));
        });
    }

    private void loadConcepts(ConceptActivator af){
        Set<String> line = new HashSet<>();

        line.addAll(FilesIO.loadAllLines("files/fcm-files/56.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/56_2.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/57.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/58.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/59.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/88.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/89.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/92.txt"));


        line.forEach(conceptLine -> loadConceptsByLine(af, conceptLine));
    }

    private void loadConceptsByLine(ConceptActivator af, String lineFromFile){
        String[] extractedConceptNames = lineFromFile.split(":");
        String[] extractedSecountPartOfExtractedConceptNames = extractedConceptNames[1].split("="); //sorry, i didnt find better name
        String conceptName1 = extractedConceptNames[0];
        String conceptName2 = extractedSecountPartOfExtractedConceptNames[0];

        System.out.println(conceptName1);
        System.out.println(conceptName2);

        Concept c1 = new Concept(conceptName1, null, af, 0.0, resolveConceptOutput(conceptName1), false);
        Concept c2 = new Concept(conceptName2, null, af, 0.0, resolveConceptOutput(conceptName2), false);

        concepts.add(c1);
        concepts.add(c2);
    }

    private double resolveConceptOutput(String conceptName){
        if (ResolverNew.isCodeInResolvedList(conceptName))
            return 0.1;
        else
            return 0.0;
    }
}
