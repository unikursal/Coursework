package Controller;

import Util.HibernateUtil;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by User on 26.07.20
 **/

  public class ControllerLog {
    @FXML
    private TextField logField;

    @FXML
    private PasswordField passField;

    @FXML
    private AnchorPane AnchorPane;

    private String login = "root";
    private String pass = "root";
    private Stage prStage;

    public void initialize(){
        //Log.createLog("D:log.log",prStage);

        Thread th = new Thread(new HibernateUtil());
        th.setDaemon(true);
        th.start();
    }

    public void setPrStage(Stage stage){
        prStage = stage;
    }

    @FXML
    public void onMouseClicked(){
        if(logField.getText().equals(login) && passField.getText().equals(pass)) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/root.fxml"));
                Parent root = loader.load();
                ControllerRoot controllerRoot = loader.getController();
                controllerRoot.setPrStage(prStage);
                prStage.setScene(new Scene(root, 1200, 800));
            } catch (IOException e) {
                ShowText.show("Не знайдено файл root.fxml \n", AnchorPane, prStage );

            }
        }else{
            ShowText.show("Невірний пароль або логін \n", AnchorPane , prStage);
        }
    }

    public void keyReleased(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER)
            onMouseClicked();
    }
}
