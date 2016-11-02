package Controller;

import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by User on 03.08.2016.
 */
public abstract class Controller {

    public abstract void setStage(Stage stage);

    public abstract void setTableView(TableView table);

    public abstract void setParentPane(Pane parentPane);
}
