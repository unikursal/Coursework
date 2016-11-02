package Controller;

import ObjectTable.Cause;
import ObjectTable.Witness;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerWitness extends Controller implements InterfaceControl{
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
    private TextField fieldForNumPh;

    @FXML
    private DatePicker datePicker;

    public void clickSave(MouseEvent event){
        String name = fieldForName.getText();
        String lName = fieldForLName.getText();

        LocalDate ld = datePicker.getValue();
        Date date = null;
        if(ld != null)
            date = Date.valueOf(ld);

        String addr = fieldForAddr.getText();

        String strWithNumPh = fieldForNumPh.getText();
        long numPh = -1;
        if(!strWithNumPh.isEmpty())
            numPh = Long.parseLong(strWithNumPh);

        Witness witness = new Witness(name, lName, date, addr, numPh);

        parentPane.setDisable(false);
        cause.addWitness(witness);

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
