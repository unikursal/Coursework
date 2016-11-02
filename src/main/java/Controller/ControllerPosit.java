package Controller;

import ObjectTable.ObjectData;
import ObjectTable.Posit;
import Util.HibernateUtil;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by User on 19.10.2016.
 */
public class ControllerPosit extends Controller {
    Stage stage;
    Pane parentPane;
    TableView<ObjectData> tableView;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField textField;

    @FXML
    private Button butAdd;

    public void clickSave(MouseEvent event){
        if(textField.getText().isEmpty()){
            ShowText.show("Є незаповнені поля", gridPane, stage);
            return;
        }

        Posit posit = new Posit(textField.getText());

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == null", parentPane, stage);
            return;
        }


        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(posit);
            session.save(posit);
            session.flush();
            transaction.commit();
        }catch (HibernateException e){
            if (transaction != null)
                transaction.rollback();
            ShowText.show(e.getMessage(), parentPane, stage);
        }finally {
            session.close();
        }

        parentPane.setDisable(false);
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setTableView(TableView table) {
        this.tableView = table;
    }

    @Override
    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }
}
