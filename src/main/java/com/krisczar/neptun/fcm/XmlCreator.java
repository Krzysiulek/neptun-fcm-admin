package com.krisczar.neptun.fcm;

import com.krisczar.neptun.Resolver;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XmlCreator {
    private Set<String> uQas = new HashSet<>();

    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private Element mapsElement;
    private Element mapElement;
    private Element conceptsElement;
    private Element connectionsElement;

    // TODO: create concepts && connections

    // TODO: change WT:SZ to SZ:WT in file 56



    XmlCreator(){
        loadUserQAs();

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
        createConcepts();
        createConnections();
        saveMapToFile("map.xml");
    }

    private void createConcepts(){

    }

    private void createConnections(){

    }

    private void createConcept(){

    }

    private void createConnection(){

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

}

