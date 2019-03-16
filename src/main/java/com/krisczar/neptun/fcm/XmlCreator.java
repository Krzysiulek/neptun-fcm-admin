package com.krisczar.neptun.fcm;

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

public class XmlCreator {
    private DocumentBuilderFactory dbFactory;
    private DocumentBuilder dBuilder;
    private Document doc;

    private Element mapsElement;
    private Element mapElement;
    private Element conceptsElement;
    private Element connectionsElement;

    XmlCreator(){
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

}

