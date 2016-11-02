package Util;

import Controller.ControllerLog;
import ObjectTable.ObjectData;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by User on 26.07.2016.
 */
public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 900, 600));
        ControllerLog controller = loader.getController();
        controller.setPrStage(primaryStage);
        primaryStage.setOnCloseRequest((WindowEvent e) -> HibernateUtil.closeSessionFactory());
        primaryStage.show();
    }
}
