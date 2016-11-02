package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerRepresentativesOnP extends Controller{
    private TableView tableView;
    private Stage stage;
    private Pane parentPane;

    @FXML
    private TextField fieldForName;

    @FXML
    private TextField fieldForLName;

    @FXML
    private TextField fieldForAddr;

    @FXML
    private TextField fieldForStatus;

    public void clickSave(MouseEvent event){}

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setTableView(TableView table){
        tableView = table;
    }

    @Override
    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }
}
