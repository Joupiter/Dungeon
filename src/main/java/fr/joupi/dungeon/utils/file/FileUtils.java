package fr.joupi.dungeon.utils.file;

import fr.joupi.dungeon.utils.threading.MultiThreading;
import lombok.experimental.UtilityClass;

import java.io.*;

@UtilityClass
public class FileUtils {

    public void createFile(File file) {
        MultiThreading.runAsync(() -> {
            if (!file.exists())
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
        });
    }

    public void save(File file, String text) {
        MultiThreading.runAsync(() -> {
            try {
                FileWriter fileWriter = new FileWriter(file);

                fileWriter.write(text);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public String loadContent(File file) {
        if (file.exists())
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null)
                    stringBuilder.append(line);

                reader.close();

                return stringBuilder.toString();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        return "";
    }

}