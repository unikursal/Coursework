package Controller;

import Listener.MyKeyReleased;
import ObjectTable.*;
import Util.DataWithNewThread;
import Util.HibernateUtil;
import Util.ShowText;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;
import java.lang.String;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by User on 03.08.2016.
 */
public class ControllerCause extends Controller{
    private TableView tableView;
    private Stage stage;
    private static final String nameLabeForNum = "НОМЕР";
    private Pane parentPane;
    private StringBuilder sbForJudg = new StringBuilder();
    private StringBuilder sbForAs = new StringBuilder();
    private StringBuilder sbForWork = new StringBuilder();
    private DataWithNewThread dataWithNewThread;
    private boolean flagInizialCombBox = false;
    private Cause cause = new Cause();

    @FXML
    private TabPane tabPane;

    @FXML
    private TextField fieldForNum;

    @FXML
    private ChoiceBox<String> boxForForm;

    @FXML
    private ChoiceBox<String> boxForCategor;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea textArea;

    @FXML
    private ComboBox<ObjectData> boxJudg;

    @FXML
    private ComboBox<ObjectData> boxAsses;

    @FXML
    private ComboBox<ObjectData> boxCourtWork;

    @FXML
    private ChoiceBox<String> chBoxForEntit;

    public void initialize(){
        dataWithNewThread = new DataWithNewThread();

        Thread thread = new Thread(dataWithNewThread);
        thread.start();

        boxJudg.setCellFactory((boxJudg) -> {
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
        boxJudg.setOnKeyReleased(new MyKeyReleased(boxJudg));
        boxJudg.setVisibleRowCount(5);


        boxAsses.setCellFactory((boxJudg) -> {
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
        boxAsses.setOnKeyReleased(new MyKeyReleased(boxAsses));
        boxAsses.setVisibleRowCount(5);


        boxCourtWork.setCellFactory((boxJudg) -> {
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
        boxCourtWork.setOnKeyReleased(new MyKeyReleased(boxCourtWork));
        boxCourtWork.setVisibleRowCount(5);
    }

    public void clickSave(MouseEvent event){
        if(fieldForNum.getText().isEmpty()){
            ShowText.show("Поле " + nameLabeForNum + " є обов'язковим для заповнення \n", tabPane, stage);
            return;
        }

        String num = fieldForNum.getText();

        int form = boxForForm.getSelectionModel().getSelectedIndex();
        int categor = boxForCategor.getSelectionModel().getSelectedIndex();

        LocalDate ld = datePicker.getValue();
        Date date = null;
        if(ld != null)
            date = Date.valueOf(ld);

        String string = textArea.getText();

        cause.setParametr(num, form, categor, date, string);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == nul", parentPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.persist(cause);
            session.save(cause);
            session.flush();
            transaction.commit();
        }catch (HibernateException e){
            if(transaction != null)
                transaction.rollback();
            ShowText.show(e.getMessage(), null, null );
        }finally {
            session.close();
        }

        parentPane.setDisable(false);
        stage.close();
    }

    public void clickAddInf(){
        if(chBoxForEntit.getValue() == null)
            return;

        String path = "/select.fxml";

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        if(loader.getLocation() == null){
            ShowText.show("Не знайдено розташування файлу " + path, tabPane, stage);
            return;
        }
        try {
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setOnCloseRequest(event -> tabPane.setDisable(false));

            ControllerAddInf controllerAddInf = loader.getController();
            controllerAddInf.setStage(stage);
            controllerAddInf.setParentPane(tabPane);
            controllerAddInf.setCause(cause);

            String nameTable = ControllerRoot.getMapForChoiceBox().get(chBoxForEntit.getValue());
            controllerAddInf.setNameCurTable(nameTable);

            tabPane.setDisable(true);
            stage.show();
        }catch (IOException e){
            ShowText.show(e.getMessage(), tabPane, stage);
            return;
        }
    }

    public void selectChange(){
        if(flagInizialCombBox)
            return;

        flagInizialCombBox = true;

        ArrayList[] lists = dataWithNewThread.getDatas();
        if(lists == null){
            ShowText.show("Помилка ініціалізації ComboBox in Cause", tabPane, null);
            stage.close();
            return;
        }
        boxJudg.setItems(FXCollections.observableArrayList(lists[0]));

        boxAsses.setItems(FXCollections.observableArrayList(lists[1]));

        boxCourtWork.setItems(FXCollections.observableArrayList(lists[2]));
    }

    private void saveSecondInf(){
        Set<Judge> judgeSet = cause.getJudgeSet();
        Set<Assessor> assessorSet = cause.getAssessorSet();
        Set<CourtWork> courtWorkSet = cause.getCourtWorkSet();
        Set<OneParty> onePartySet = cause.getOnePartySet();
        Set<OtherParty> otherPartySet = cause.getOtherPartySet();
        Set<Documents> documentsSet = cause.getDocumentsSet();
        Set<Evidence> evidenceSet = cause.getEvidenceSet();
        Set<Request> requestSet = cause.getRequestSet();
        Set<Trial> trialSet = cause.getTrialSet();
        Set<Witness> witnessSet = cause.getWitnessSet();

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
