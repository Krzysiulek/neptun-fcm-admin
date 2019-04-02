package RulesResolver;

import java.io.*;

public class FilesIO {

    public static void save(String newDirectoryName, String fileName, String fileExtension ,String toSave){
        makeDir(newDirectoryName);

        try {
            String pathWithDirecotry = newDirectoryName + "/" + fileName + "." + fileExtension;

            FileWriter fileWriter = new FileWriter(pathWithDirecotry);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(toSave);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDir(String directoryName){
        String[] directories = directoryName.split("/");
        String summaryPath = "";

        for(String dir : directories) {
            summaryPath += dir + "/";
            File directory = new File(summaryPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

        }


    }
}
