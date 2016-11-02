package Controller;

import Listener.MyKeyReleased;
import ObjectTable.Court;
import ObjectTable.CourtWork;
import ObjectTable.ObjectData;
import Util.HibernateUtil;
import Util.ShowText;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerCourtWork extends Controller{
    private TableView tableView;
    private Stage stage;
    private Pane parentPane;
    StringBuilder sb = new StringBuilder();

    @FXML
    private TextField fieldForName;

    @FXML
    private TextField fieldForLName;

    @FXML
    private TextField fieldForPosit;

    @FXML
    private ComboBox<ObjectData> boxForCourt;

    public void initialize(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == null", parentPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        List<Court> courts = session.createQuery("from Court").list();

        boxForCourt.setCellFactory((boxJudg) -> {
            return new ListCell<ObjectData>() {
                @Override
                protected void updateItem(ObjectData data, boolean empty) {
                    super.updateItem(data, empty);
                    if (data == null || empty)
                        setText(null);
                    else
                        setText(data.getValue1().getValue() + " " + data.getValue2().getValue());
                }
            };
        });
        boxForCourt.setOnKeyReleased(new MyKeyReleased(boxForCourt));
        boxForCourt.setVisibleRowCount(5);

        boxForCourt.setItems(FXCollections.observableArrayList(courts));
    }

    public void clickSave(MouseEvent event){
        if(boxForCourt.getValue() == null) {
            ShowText.show("Поле код суду є обов'язковим для вибору", null, null);
            return;
        }

        String name = fieldForName.getText();
        String lName = fieldForLName.getText();
        String posit = fieldForPosit.getText();
        int kocCourt = Integer.parseInt(boxForCourt.getValue().getValue1().getValue());

        CourtWork courtWork = new CourtWork(name, lName, posit, kocCourt );

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == null", parentPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(courtWork);
            session.save(courtWork);
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
    public void setTableView(TableView table){
        tableView = table;
    }

    @Override
    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }
}
