package Util;

import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * Created by User on 14.08.2016.
 */
public class MainReader {
private static String pathToFileSettings = "settings.txt";

    public static Map<String, String> getMap(String fileName, Parent parent, Stage st){
        Map<String, String> map = new HashMap<>();
        try {
            if(MainReader.class.getResource(fileName) == null) {
                ShowText.show("Не знайдено файл " + fileName, parent, st);
                return map;
            }

            FileReader reader = new FileReader(MainReader.class.getResource(fileName).getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str = bufferedReader.readLine();
            String phrase[] = str.split("/");
            for(String word: phrase){
                String s[] = word.split("=");
                if(s.length == 2)
                    map.put(s[0],s[1]);
            }
            bufferedReader.close();
            reader.close();

        }catch (FileNotFoundException e){
          ShowText.show(e.getMessage(), parent, st);
        }catch (IOException e1){
            ShowText.show(e1.getMessage(), parent, st);
        }
        return map;
    }

    public static Map<String, Map<String, String>> getMapTable(Parent parent, Stage st){
        Map<String, Map<String, String>> map = new HashMap<>();

        try{
            FileReader reader = new FileReader(MainReader.class.getResource("/f2.txt").getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            String str;
            while((str = bufferedReader.readLine()) != null){

                String phrase[] = str.split("/");
                Map<String, String> childMap = new HashMap<>();

                if(phrase.length == 2){
                    String phr[] = phrase[1].split("-");
                    for(String s: phr){
                        String ph[] = s.split("=");
                        childMap.put(ph[0], ph[1]);
                    }
                }

                map.put(phrase[0], childMap);
            }
        }catch (FileNotFoundException e){
            ShowText.show(e.getMessage(), parent, st);
        }catch (IOException e1){
            ShowText.show(e1.getMessage(), parent, st);
        }
        return map;
    }

    public static String getDefaultValue(String str, Stage st){
        try{
            FileReader reader = new FileReader(MainReader.class.getResource(pathToFileSettings).getPath());
            BufferedReader bufferedReader = new BufferedReader(reader);
            while((str = bufferedReader.readLine()) != null) {
                String s1 = str.split("=")[0];
                if(s1.equals(str))
                    return str.split("=")[1];
            }
        }catch (FileNotFoundException e){
            ShowText.show(e.getMessage(), null, st);
        }catch (IOException e1){
            ShowText.show(e1.getMessage(), null, st);
        }
        return "";
    }

}
