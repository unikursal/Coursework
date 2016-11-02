package Controller;

import ObjectTable.Cause;
import ObjectTable.Documents;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerDocuments extends Controller implements InterfaceControl{
    private TableView tableView;
    private Stage stage;
    private TabPane parentPane;
    private Cause cause;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField fieldForNum;

    @FXML
    private TextArea textArea;

    public void clickSave(MouseEvent event){
        if(fieldForNum.getText().isEmpty()){
            ShowText.show("Поле Номер є обов'язковим для заповнення \n", gridPane, stage);
            return;
        }

        int num = Integer.parseInt(fieldForNum.getText());
        String descr = textArea.getText();

        Documents documents = new Documents(num, descr);

        cause.addDocuments(documents);

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
