package Controller;

import ObjectTable.Cause;
import ObjectTable.Request;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerRequest extends Controller implements InterfaceControl{
    private TableView tableView;
    private Stage stage;
    private TabPane parentPane;
    private Cause cause;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField fieldForNum;

    @FXML
    private DatePicker datePicker;

    @FXML
    private DatePicker datePickerAnsw;

    @FXML
    private TextArea textArea;

    public void clickSave(MouseEvent event){
        if(fieldForNum.getText().isEmpty()){
            ShowText.show("Поле Номер є обов'язковим для заповнення \n", gridPane, stage);
            return;
        }

        int num = Integer.parseInt(fieldForNum.getText());

        LocalDate ld = datePicker.getValue();
        Date datBegin = null;
        if(ld != null)
            datBegin = Date.valueOf(ld);

        ld = datePickerAnsw.getValue();
        Date dateAnsw = null;
        if(ld != null)
            dateAnsw= Date.valueOf(ld);

        String descr = textArea.getText();

        Request request = new Request(num, descr, datBegin, dateAnsw);

        cause.addRequest(request);

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
