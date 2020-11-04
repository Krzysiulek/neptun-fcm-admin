package com.krisczar.neptun.FCM;

import com.krisczar.neptun.RulesResolver.ResolverNew;
import com.krisczar.neptun.SupportServices.FilesIO;
import com.krisczar.neptun.TOs.FCMPreferences;
import org.megadix.jfcm.CognitiveMap;
import org.megadix.jfcm.Concept;
import org.megadix.jfcm.ConceptActivator;
import org.megadix.jfcm.conn.WeightedConnection;
import org.megadix.jfcm.utils.FcmIO;

import java.io.*;
import java.util.*;

public class FCMFileLoader {
	private CognitiveMap map = new CognitiveMap("FCM");
	private Map<String, Double> wagsMap = new HashMap<>();
	private Set<String> conceptsSet = new HashSet<>();
	private Set<String> line = new HashSet<>();

	private List<Concept> concepts = new ArrayList<>();
	static int counter = 0;

	Set<String> OPs = new HashSet<>();
	Set<String> WTs = new HashSet<>();

	public FCMFileLoader(ConceptActivator af) {
		if(FCMPreferences.isExternalFilesLoading)
			loadExternalFiles();
		else
			loadAllFiles();

		loadWags();
		loadConcepts(af);
		loadConnections();

		if (FCMPreferences.isLoadingConceptWeightsFromFile){
			createDefaultConceptsWags();
		}

		if (FCMPreferences.isLoadingConnectionsWeightsFromFile){
			createDefaultConnectionsWags();
		}

		try {
			FcmIO.saveAsXml(map, "defaultNeptunMap.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CognitiveMap getMap() {
		return map;
	}

	private List<String> loadAllLinesFromFileUtf8(String filePathWithName) {
		File file = new File(filePathWithName);
		List<String> data = new LinkedList<String>();
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))) {
			String line = null;
			while ((line = in.readLine()) != null) {
				data.add(line);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	private void loadWags() {
		final String fileWagsFile = "files/fcm-files/52.txt";
		List<String> wagsData = loadAllLinesFromFileUtf8(fileWagsFile);

		wagsData.forEach(line -> {
			String[] wagsPair = line.split(";");
			wagsMap.put(wagsPair[0], Double.valueOf(wagsPair[1]));
		});

	}

	private void loadAllFiles() {
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/56.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/56_2.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/57.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/58.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/59.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/88.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/89.txt"));
		line.addAll(loadAllLinesFromFileUtf8("files/fcm-files/92.txt"));

		// te pliki nalezy odkomentowac w celu przywrocenia polaczen OP:WT w mapie
        line.addAll(FilesIO.loadAllLines("files/fcm-files/130.txt"));
        line.addAll(FilesIO.loadAllLines("files/fcm-files/132.txt"));

//        loadAdditionalLines();
	}

	private void loadExternalFiles(){
		FilesIO.getAllFiles("files/additional_fcm_files")
				.forEach(file -> {
					line.addAll(loadAllLinesFromFileUtf8(file.getAbsolutePath()));
				});
	}

	private void loadAdditionalLines() {
		// loading all OPs
		ResolverNew.getResolvedVariables().forEach(variableNew -> {
			if (variableNew.getName().matches("OP\\d+")) {
				OPs.add(variableNew.getName());
			}
		});

		Set<String> all130Lines = new HashSet<>();
		all130Lines.addAll(loadAllLinesFromFileUtf8("files/fcm-files/130.txt"));

		// loading all WTs
		all130Lines.forEach(line -> {
			String[] extractedTmp1 = line.split(":");
			String[] extractedTmp2 = extractedTmp1[1].split("=");

			String conceptOP = extractedTmp1[0];
			String conceptWT = extractedTmp2[0];

			if (isCodeInOPs(conceptOP)) {
				WTs.add(conceptWT);
			}

		});

//        WTs.size();
	}

	private void loadConcepts(ConceptActivator af) {
		try{
			line.forEach(conceptLine -> createConceptsByLine(conceptLine));
			conceptsSet.forEach(con -> finalCreationOfConcept(con, af));
		}
		catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}

	}

	private void createConceptsByLine(String lineFromFile) {
		String conceptName1;
		String conceptName2;

		try{
			String[] extractedConceptNames = lineFromFile.split(":");
			String[] extractedSecountPartOfExtractedConceptNames = extractedConceptNames[1].split("="); // sorry, i didnt

			conceptName1 = extractedConceptNames[0];
			conceptName2 = extractedSecountPartOfExtractedConceptNames[0];
			conceptsSet.add(conceptName1);
			conceptsSet.add(conceptName2);
		}
		catch (ArrayIndexOutOfBoundsException e){
			System.out.println(lineFromFile);
			String[] extractedConceptNames = lineFromFile.split(";");

			if(extractedConceptNames.length > 2){
				conceptName1 = extractedConceptNames[0];
				conceptName2 = extractedConceptNames[1];
				conceptsSet.add(conceptName1);
				conceptsSet.add(conceptName2);
			}
		}
	}

	private void finalCreationOfConcept(String conceptName, ConceptActivator af) {
		Concept c1 = new Concept(conceptName, null, af, 0.0, resolveConceptOutput(conceptName), false);

		concepts.add(c1);

		map.addConcept(c1);
	}

	private double resolveConceptOutput(String conceptName) {
		if (ResolverNew.isCodeInResolvedList(conceptName)) {
			return 0.0;
		}

		else {
			return 0.0;
		}
	}

	private void loadConnections() {
		for (String lineFromFile : line) {
			createConnection(lineFromFile);
		}

		System.out.println("Counter: " + counter);
	}

	private void createConnection(String lineFromFile) {
		String concept1 = "";
		String concept2 = "";
		String weightFromFile = "WAG1";

		try{
			String[] extractedTmp1 = lineFromFile.split(":");
			String[] extractedTmp2 = extractedTmp1[1].split("=");

			concept1 = extractedTmp1[0];
			concept2 = extractedTmp2[0];
			weightFromFile = extractedTmp2[1];
		}catch (ArrayIndexOutOfBoundsException e){
			String[] extractedTmp = lineFromFile.split(";");

			if(extractedTmp.length == 3){
				concept1 = extractedTmp[0];
				concept2 = extractedTmp[1];
				weightFromFile = extractedTmp[2];
			}
		}


		double weight;
		try {
			if ((wagsMap != null)&&(wagsMap.containsKey(weightFromFile))){
			weight = wagsMap.get(weightFromFile);
			}
			else {
				weight = 0.0;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			weight = 0.0;
		}

//        System.out.print(new StringBuilder(concept1)
//                .append("-").append(weight)
//                .append("->")
//                .append(concept2).append("\t"));
		if (!(weight == 0.0))
			weight += getAdditionalWeight(concept1, concept2); // its adding additional value

//        System.out.println(new StringBuilder(concept1)
//                .append("-").append(weight)
//                .append("->")
//                .append(concept2));

		String connectionName = concept1 + " -> " + concept2;
		WeightedConnection conn_1 = new WeightedConnection(connectionName, null, weight);

		try{
			map.addConnection(conn_1);
			map.connect(concept1, connectionName, concept2);
		}catch (IllegalArgumentException e){
			e.printStackTrace();
		}


	}

	private double getAdditionalWeight(String conceptWhileCreating1, String conceptWhileCreating2) {
//        if (conceptWhileCreating1.matches("WT\\d+")){
//            if (isCodeInWTs(conceptWhileCreating1)){
//                System.out.println("Adding " + conceptWhileCreating1 + " -> " + conceptWhileCreating2);
//                counter++;
//                return 0.5;
//            }
//        }
//
//        else if (conceptWhileCreating2.matches("WT\\d+")){
//            if (isCodeInWTs(conceptWhileCreating2)){
//                System.out.println("Adding " + conceptWhileCreating1 + " -> " + conceptWhileCreating2);
//                counter++;
//                return 0.5;
//            }
//        }

		return 0.0;
	}

	private boolean isCodeInConcepts(String code) {
		for (String concept : conceptsSet) {
			if (code.equals(concept))
				return true;
		}
		return false;
	}

	private boolean isCodeInWTs(String code) {
		for (String wt : WTs) {
			if (wt.equals(code))
				return true;
		}
		return false;
	}

	private boolean isCodeInOPs(String code) {
		for (String op : OPs) {
			if (op.equals(code))
				return true;
		}
		return false;
	}

	private void createDefaultConnectionsWags(){
		List<String> defaultConnectionsWags = new ArrayList<>();

		FilesIO.getAllFiles("files/default_fcm_values/connections")
				.forEach(file -> {
					defaultConnectionsWags.addAll(FilesIO.loadAllLines(file.getAbsolutePath()));
				});

		defaultConnectionsWags
				.forEach(fileLine ->{
					String[] separetedLine = fileLine.split(";");
					String from = separetedLine[0];
					String to = separetedLine[1];
					double weight;

					try {
						weight = wagsMap.get(separetedLine[2]);
					}
					catch (Exception e){
                        System.out.println("Weight not found for Connection [" + fileLine + "]");
						weight = 0;
					}

					try{
						map.removeConnection(from + " -> " + to);

						WeightedConnection connection = new WeightedConnection(from + " -> " + to, "", weight);
						map.addConnection(connection);
						map.connect(from, connection.getName(), to);
					}
					catch (Exception e){
						System.out.println("Cannot remove connection. It doesnt exists [" + fileLine + "]");
					}
				});
	}

	private void createDefaultConceptsWags(){
        List<String> defaultConceptsWags = new ArrayList<>();

        FilesIO.getAllFiles("files/default_fcm_values/concepts")
                .forEach(file -> {
                    defaultConceptsWags.addAll(FilesIO.loadAllLines(file.getAbsolutePath()));
                });

        defaultConceptsWags
                .forEach(fileLine ->{
                    String[] separetedLine = fileLine.split(";");
                    String conceptName = separetedLine[0];
                    double weight;

                    try {
                        weight = wagsMap.get(separetedLine[1]);
                    }
                    catch (Exception e){
                        System.out.println("Weight not found for Concept [" + fileLine + "]");
                        weight = 0;
                    }

                    try{
                        map.getConcept(conceptName).setOutput(weight);
                    }
                    catch (Exception e){
                        System.out.println("Cannot remove connection. It doesnt exists");
                    }
                });
	}
}
