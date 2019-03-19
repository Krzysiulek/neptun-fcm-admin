package com.krisczar.neptun.fcm;

import com.krisczar.neptun.Resolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class XmlCreator {
    private Set<String> uQas = new HashSet<>();
    private Set<String> conceptVars = new HashSet<>();
    private Set<String> connectionVars = new HashSet<>();
    private Map<String, String> weights = new HashMap<>();

    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private Element mapsElement;
    private Element mapElement;
    private Element connectionsElement;
    private Element conceptsElement;

    // TODO: create concepts && connections

    // TODO: change WT:SZ to SZ:WT in file 56



    public XmlCreator(){
//        loadUserQAs();

        try{
            dbFactory =
                    DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();

            // creating skeleton
            mapsElement = doc.createElement("maps");
            mapsElement.setAttribute("xmlns", "https://neptun-fcm.herokuapp.com/");
            doc.appendChild(mapsElement);

            mapElement = doc.createElement("map");
            mapElement.setAttribute("name", "Neptun-FCM");
            mapsElement.appendChild(mapElement);

            conceptsElement = doc.createElement("concepts");
            mapElement.appendChild(conceptsElement);

            connectionsElement = doc.createElement("connections");
            mapElement.appendChild(connectionsElement);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mapCreator(){
        loadUserQAs();
        loadWeights("files/fcm-files/52.txt");

        loadVarsFromFile("files/fcm-files/56.txt");
        loadVarsFromFile("files/fcm-files/57.txt");
        loadVarsFromFile("files/fcm-files/88.txt");

        createConcepts();
        createConnections();
        saveMapToFile("map.xml");
    }

    private void createConcepts(){
        conceptVars.forEach(var -> createConcept(var));
    }

    private void loadVarsFromFile(String fileName){
        File file =
                new File(fileName);

        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            line = line.replaceAll(" ", "");
            line = line.replaceAll("\uFEFF", "");


            String[] tmp = line.split(":");
            String[] tmp2 = tmp[1].split("=");

            conceptVars.add(tmp[0]);
            conceptVars.add(tmp2[0]);
            connectionVars.add(line);
        }

    }

    private void loadWeights(String fileName){
        File file =
                new File(fileName);

        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine()){
            String line = sc.nextLine();
            line = line.replaceAll(" ", "");
            line = line.replaceAll("\uFEFF", "");

            String tmp[] = line.split(";");
            weights.put(tmp[0], tmp[1]);
        }
    }


    private void createConnections(){
        connectionVars.forEach(connection -> createConnection(connection));
    }

    private void createConcept(String var){
        // TODO
        // <concept act="SIGNUM" input="0.0" name="Food" output="0.0" />
        Element conceptElement;

        String inputValue = ifExistsInUQA(var);

        conceptElement = doc.createElement("concept");
        conceptElement.setAttribute("act", "SIGNUM");
        conceptElement.setAttribute("input", inputValue);
        conceptElement.setAttribute("name", var);
        conceptElement.setAttribute("output", "0.0");
        conceptsElement.appendChild(conceptElement);
    }

    private void createConnection(String connection){
        String[] tmp = connection.split(":");
        String[] tmp2 = tmp[1].split("=");

        String from = tmp[0];
        String to = tmp2[0];
        String wag = weights.get(tmp2[1]);

//        System.out.println("Create connection: " + tmp[0] + " " + tmp[1]);

        /*
        <connection from="Erbivores" name="Erbivores - Grassland" to="Grassland" type="WEIGHTED">
                <params>
                    <param name="weight" value="-0.6"/>
                </params>
        </connection>
         */
        Element connectionElement = doc.createElement("connection");
        connectionElement.setAttribute("from", from);
        connectionElement.setAttribute("name", from + " - " + to);
        connectionElement.setAttribute("to", to);
        connectionElement.setAttribute("type", "WEIGHTED");
        connectionsElement.appendChild(connectionElement);

        Element paramsElement = doc.createElement("params");
        connectionElement.appendChild(paramsElement);

        Element paramElement = doc.createElement("param");
        paramElement.setAttribute("name", "weight");
        paramElement.setAttribute("value", wag);
        paramsElement.appendChild(paramElement);



    }

    private void saveMapToFile(String fileName){
        try{
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
        }
        catch (Exception e){
         e.printStackTrace();
        }
    }

    private void loadUserQAs(){
        List<String> userQAs = Resolver.getQAs();

        userQAs.forEach(code -> {
                if(code.matches("^R\\d*") ||
                        code.matches("^WT\\d*") ||
                        code.matches("^SZ\\d*") ||
                        code.matches("^WWT\\d*") ||
                        code.matches("^ZOI\\d*")) {
                    uQas.add(code);
                }

        });



    }

    private String ifExistsInUQA(String code){
        for(String tmp : uQas){
            if (tmp.equals(code)){
                return "1.0";
            }
        }

        return "0.0";
    }

}

