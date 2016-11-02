package Controller;

import ObjectTable.Cause;
import ObjectTable.ObjectData;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by User on 26.09.2016.
 */
public class ControllerAddInf {
    private String nameCurTable;
    private Stage stage;
    private TabPane parentPane;
    private Cause cause;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<ObjectData> comboBox;

    public void clickAddNew(){
        if(nameCurTable == null) {
            ShowText.show("Помилка отримання nameCurTable", gridPane, stage);
            return;
        }

        String path = "/" + nameCurTable + ".fxml";

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        if(loader.getLocation() == null){
            ShowText.show("Не знайдено розташування " + path, gridPane, stage);
            return;
        }

        try{
            Parent parent = loader.load();

            Controller controller = loader.getController();

            controller.setStage(stage);

            InterfaceControl interfaceControl = (InterfaceControl)controller;
            interfaceControl.setCause(cause);
            interfaceControl.setTabPane(parentPane);

            stage.setScene(new Scene(parent));

        }catch (IOException e){
            ShowText.show(e.getMessage(), gridPane, stage);
            return;
        }
    }

    public void clickAddExist(){

    }

    public void setCause(Cause cause){
        this.cause = cause;
    }

    public void setNameCurTable(String nameCurTable) {
        this.nameCurTable = nameCurTable;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParentPane(TabPane parentPane) {
        this.parentPane = parentPane;
    }
}
