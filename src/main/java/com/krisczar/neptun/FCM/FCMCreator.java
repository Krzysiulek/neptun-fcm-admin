package com.krisczar.neptun.FCM;

import com.krisczar.neptun.GUI.ActivatorOptions;
import com.krisczar.neptun.GUI.ResultActivatorMenu;
import com.krisczar.neptun.ModelResolver;
import com.krisczar.neptun.SupportServices.FilesIO;
import org.megadix.jfcm.*;
import org.megadix.jfcm.act.*;
import org.megadix.jfcm.utils.FcmIO;
import org.megadix.jfcm.utils.FcmRunner;
import org.megadix.jfcm.utils.SimpleFcmRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

public class FCMCreator {
    CognitiveMap map;
    FcmRunner runner;
    long userId;

    String activator;
    double maxDelta;
    int maxEpochs;


    public FCMCreator() {
        ConceptActivator af = new SigmoidActivator();
        FCMFileLoader fcmFileLoader = new FCMFileLoader(af);
        map = fcmFileLoader.getMap();
    }

    public FCMCreator(long userId, String activator) {
        ConceptActivator af = new GaussianActivator();
        this.activator = activator;

        switch (activator){
            case "Cauchy Activator":
                af = new CauchyActivator();
                break;

            case "Gaussian Activator":
                af = new GaussianActivator();
                ((GaussianActivator) af).setWidth(ActivatorOptions.activatorWidth);
                break;

            case "Hyperbolic Tangent Activator":
                af = new HyperbolicTangentActivator();
                break;

            case "Interval Activator":
                af = new IntervalActivator();
                ((IntervalActivator) af).setZeroValue(ActivatorOptions.activatorZeroValue);
                ((IntervalActivator) af).setAmplitude(ActivatorOptions.activatorAmplitude);
                break;

            case "Linear Activator":
                af = new LinearActivator();
                ((LinearActivator) af).setFactor(ActivatorOptions.activatorFactor);
                ((LinearActivator) af).setMin(ActivatorOptions.activatorMin);
                ((LinearActivator) af).setMax(ActivatorOptions.activatorMax);
                break;

            case "Nary Activator":
                af = new NaryActivator();
                ((NaryActivator) af).setN(ActivatorOptions.activatorN);
                break;

            case "Sigmoid Activator":
                af = new SigmoidActivator();
                ((SigmoidActivator) af).setK(ActivatorOptions.activatorK);
                break;

            case "Signum Activator":
                af = new SignumActivator();
                ((SignumActivator) af).setZeroValue(ActivatorOptions.activatorZeroValue);
                break;
        }



        FCMFileLoader fcmFileLoader = new FCMFileLoader(af);
        map = fcmFileLoader.getMap();
        this.userId = userId;
    }

    public void run(){
        runner = new SimpleFcmRunner(map, 0.1, 2);
        runner.run();
        saveRaw();
        saveProcessed();
    }

    public void run(double maxDelta, int maxEpochs){
        this.maxDelta = maxDelta;
        this.maxEpochs = maxEpochs;

        runner = new SimpleFcmRunner(map, maxDelta, maxEpochs);
        runner.run();
        saveRaw();
        saveProcessed();
        saveMapAsXML();
    }

    public void saveRaw(){
        StringBuilder sum = new StringBuilder();
        sum.append(getParams());

        map.getConcepts().forEach((s, concept) ->{
            sum.append(concept).append("\n");
        });

        FilesIO.save("results/fcm/" + userId + "/raw", String.valueOf(LocalDateTime.now()), "txt", sum.toString());
    }

    public void saveProcessed(){
        FilesIO.save("results/fcm/" + userId + "/processed", String.valueOf(LocalDateTime.now()), "txt", toString());
    }

    public void saveMapAsXML(){
        String directory = "results/fcm/" + userId + "/maps/";
        String fileName = String.valueOf(LocalDateTime.now());
        String filePath = directory + fileName + ".xml";

        FilesIO.makeDir(directory);

        try {
            FcmIO.saveAsXml(map, filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getParams());


        List<String> allWT = new ArrayList<>();
        List<String> allWWT = new ArrayList<>();

        map.getConcepts().forEach((s, concept) ->{
            if (s.matches("^WT\\d+$")){
                allWT.add(s);
            }
            else if (s.matches("^WWT\\d+$")){
                allWWT.add(s);
            }
        });


        for (int i = 1; i<=56; i++){
            String WT = "WT"+i;
            result.append(WT).append(": ").append(ModelResolver.getModel(WT)).append("\n");

            int end = i * 5;
            int start = end - 4;

            String WWT = getMaxWWTOutputFromRange(start, end);
            result.append(WWT).append(": ").append(ModelResolver.getModel(WWT)).append("\n\n");
        }


        return result.toString();
    }

    private String getMaxWWTOutputFromRange(int start, int end){
        Map<String, Double> WWTs = new HashMap<>();

        for (int i = start; i <= end; i++){
            String WWTName = "WWT"+i;
//            System.out.println(WWTName);
            String name = map.getConcept(WWTName).getName();
            double output = map.getConcept(WWTName).getOutput();
            WWTs.put(name, output);
        }

        String key = Collections.max(WWTs.entrySet(), Map.Entry.comparingByValue()).getKey();

        return key;
    }

    private String getParams(){
        StringBuilder result = new StringBuilder();
        result.append("USER: ").append(userId).append("\n");
        result.append("Date: ").append(LocalDateTime.now().toString()).append("\n");
        result.append("Activator: ").append(activator).append("\n");
        result.append("Max Delta: ").append(maxDelta).append("\n");
        result.append("Max Epochs: ").append(maxEpochs).append("\n\n\n");


        return result.toString();
    }

}
