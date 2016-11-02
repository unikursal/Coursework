package Util;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Date;


/**
 * Created by User on 06.09.2016.
 */
public class Log {
    public static void createLog(String nameFile, Stage st){
        try {
            File file = new File(nameFile);
            PrintStream printStream = new PrintStream(file);

            System.setOut(printStream);

            Date date = new Date(System.currentTimeMillis());
            System.out.println(date);

        }catch (FileNotFoundException e){
            ShowText.show("Неможливо знайти фал для журналу" + e.getMessage(), null, st);
        }
    }
}
