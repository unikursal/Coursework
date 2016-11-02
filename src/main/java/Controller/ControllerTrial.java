package Controller;

import Listener.MyKeyReleased;
import ObjectTable.Court;
import ObjectTable.ObjectData;
import ObjectTable.Trial;
import ObjectTable.Witness;
import Util.HibernateUtil;
import Util.ShowText;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.*;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerTrial extends Controller{
    private boolean allowToSave = false;

    private TableView tableView;
    private Stage stage;
    private Pane parentPane;
    private StringBuilder sb = new StringBuilder();
    private StringBuilder sbForWitn = new StringBuilder();
    private boolean isGettingListWitness = false;
    private Set<Witness> witnessSet = new HashSet<>();

    private final String queryFromListNumCause = "select cause.num from Cause as cause";
    private List<String> listNumCause = new ArrayList<String>();

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField fieldForNumCaus;

    @FXML
    private TextField fieldForNumRoom;

    @FXML
    private ComboBox<ObjectData> boxForCourt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<ObjectData> boxForWitness;

    public void initialize(){
        fieldForNumCaus.focusedProperty().addListener((observable, oldValue, newValue) -> focusActivity(observable, oldValue, newValue));


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
        boxForCourt.setVisibleRowCount(5);
        boxForCourt.setOnKeyReleased(new MyKeyReleased(boxForCourt));
        boxForCourt.setItems(FXCollections.observableArrayList(courts));


        boxForWitness.setCellFactory((boxJudg) -> {
            return new ListCell<ObjectData>() {
                @Override
                protected void updateItem(ObjectData data, boolean empty) {
                    super.updateItem(data, empty);
                    if (data == null || empty)
                        setText(null);
                    else
                        setText(data.getValue2().getValue() + " " + data.getValue1().getValue());
                }
            };
        });
        boxForWitness.setVisibleRowCount(5);
        boxForWitness.setOnKeyReleased(new MyKeyReleased(boxForWitness));

        listNumCause = session.createQuery(queryFromListNumCause).list();

        session.close();
    }

    public void clickSave(MouseEvent event){
        if(fieldForNumCaus.getText().isEmpty()) {
            ShowText.show("Поле номер справи є обов'язковим для заповнення", tabPane, stage);
            return;
        }

        if(!allowToSave)
            return;

        String strForNumRoom = fieldForNumRoom.getText();
        int numRoom = 0;
        if(!strForNumRoom.isEmpty())
            numRoom = Integer.parseInt(strForNumRoom);

        LocalDate ld = datePicker.getValue();
        Date date = null;
        if(ld != null)
            date = Date.valueOf(ld);

        int codeCourt = Integer.parseInt(boxForCourt.getValue().getValue1().getValue());

        Trial trial = new Trial(date,numRoom, codeCourt);
        trial.setWitnessSet(witnessSet);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == null", parentPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(trial);
            session.save(trial);
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

    public void onSelectionChanged(){
        if(fieldForNumCaus.getText().isEmpty() || isGettingListWitness)
            return;

        SessionFactory sessionFact = HibernateUtil.getSessionFactory();
        if(sessionFact == null){
            ShowText.show("Помилка, sessionFactory == null", parentPane, stage);
            return;
        }

        Session session = sessionFact.openSession();

        int numCaus = Integer.parseInt(fieldForNumCaus.getText());

        List<Witness> witnessList = session.createQuery("from Witness as witness where witness.numCaus=" + numCaus).list();

        boxForWitness.setItems(FXCollections.observableArrayList(witnessList));

        isGettingListWitness = true;

        session.close();

        return;
    }

    public void addWitness(MouseEvent event){
        if(boxForWitness.getValue() == null)
            return;

        witnessSet.add((Witness) boxForWitness.getValue());
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

    public void focusActivity(ObservableValue< ? extends Boolean> observable, boolean oldValue, boolean newValue){
        if(newValue)
            return;

        String str = fieldForNumCaus.getText();
        if(str.isEmpty())
            return;

        for(String a: listNumCause)
            if(str.equals(a)){
                allowToSave = true;
                return;
            }

        ShowText.show("Справи з номером " + str + " в базі немає. Перевірте ще раз номер.", tabPane, stage);

    }
}
