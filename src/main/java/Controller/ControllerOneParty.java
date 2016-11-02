package Controller;

import ObjectTable.Cause;
import ObjectTable.OneParty;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerOneParty extends Controller implements InterfaceControl{
    private TableView tableView;
    private Stage stage;
    private TabPane parentPane;
    private Cause cause;

    @FXML
    private TextField fieldForName;

    @FXML
    private TextField fieldForLName;

    @FXML
    private TextField fieldForAddr;

    @FXML
    private TextField fieldForNamPh;

    @FXML
    private TextField fieldForStatus;

    public void clickSave(MouseEvent event){
        String name = fieldForName.getText();
        String lName = fieldForLName.getText();
        String addr = fieldForAddr.getText();

        String strWithNamPh = fieldForNamPh.getText();
        long numPh = -1;
        if(!strWithNamPh.isEmpty())
            numPh = Long.parseLong(strWithNamPh);

        String st = fieldForStatus.getText();

        OneParty otherParty = new OneParty(name, lName, st, numPh, addr);

        cause.addOneParty(otherParty);

        parentPane.setDisable(false);
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setTableView(TableView table){
        tableView = table;
    }

    @Override
    public void setCause(Cause cause){
        this.cause = cause;
    }

    @Override
    public void setTabPane(TabPane parentPane) {
        this.parentPane = parentPane;
    }

    @Override
    public void setParentPane(Pane pane) {}
}
