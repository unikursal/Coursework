package Controller;


import ObjectTable.Adjudication;
import Util.HibernateUtil;
import Util.ShowText;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 03.08.2016.
 */
public class ControllerAdjudication extends Controller{
    private TableView tableView;
    private Stage stage;
    private Pane parentPane;
    private boolean allowToSave = false;
    private List<Integer> listNumCause = new ArrayList<>();

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField fieldForNum;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<String>  choiceBox;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField fieldForNumCaus;

    public void initialize(){
        fieldForNumCaus.focusedProperty().addListener((observable, oldValue, newValue) -> focusActivity(observable, oldValue, newValue));

    }

    public void clickSave(MouseEvent event){
        if(fieldForNum.getText().isEmpty() || fieldForNumCaus.getText().isEmpty()) {
            ShowText.show("Є незаповнені поля\n", gridPane, stage);
            return;
        }

        int num = Integer.parseInt(fieldForNum.getText());

        LocalDate ld = datePicker.getValue();
        Date date = null;
        if(ld != null)
            date = Date.valueOf(ld);

        String forma = choiceBox.getSelectionModel().getSelectedItem();
        String text = textArea.getText();
        int numCaus = Integer.parseInt(fieldForNumCaus.getText());

        Adjudication adjudication = new Adjudication(num, date, forma, text, numCaus);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == null", gridPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(adjudication);
            session.save(adjudication);
            session.flush();
            transaction.commit();
        }catch (HibernateException e){
            if(transaction != null)
                transaction.rollback();
            ShowText.show(e.getMessage(), gridPane, stage);
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

    public void focusActivity(ObservableValue< ? extends Boolean> observable, boolean oldValue, boolean newValue){
        if(newValue)
            return;

        String str = fieldForNumCaus.getText();
        if(str.isEmpty())
            return;

        int num = Integer.parseInt(str);

        for(int a: listNumCause)
            if(a == num){
                allowToSave = true;
                return;
            }

        ShowText.show("Справи з номером " + num + " в базі немає. Перевірте ще раз номер.", gridPane, stage);

    }
}
