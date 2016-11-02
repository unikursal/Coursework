package Controller;

import ObjectTable.Court;
import Util.HibernateUtil;
import Util.ShowText;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
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


/**
 * Created by User on 01.08.2016.
 */
public class ControllerCourt extends Controller{
    private TableView tableView;
    private Stage stage;
    private static final String mapping = "ObjectTable.Court";
    private static final String nameLabeForCode = "Код";
    private Pane parentPane;
    private StringBuffer sb = new StringBuffer();

    @FXML
    private TextField fieldForCode;

    @FXML
    private TextField fieldForName;

    @FXML
    private TextField fieldForAddr;

    @FXML
    private ComboBox<String> boxForRegion;

    @FXML
    private ChoiceBox<String> boxForInst;

    public void initialize(){
        boxForRegion.setVisibleRowCount(5);
    }

    public void clickSave(MouseEvent event){
        if(fieldForCode.getText().isEmpty()){
            ShowText.show("Поле " + nameLabeForCode + " є обов'язковим для заповнення \n", parentPane, stage);
            return;
        }
        int kod = Integer.parseInt(fieldForCode.getText());
        String name = fieldForName.getText();
        String region = boxForRegion.getSelectionModel().getSelectedItem();
        String inst = boxForInst.getSelectionModel().getSelectedItem();
        String addr =fieldForAddr.getText();

        Court court = new Court(kod,name, region, inst, addr);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == nul", parentPane, stage);
            return;
        }

        Session session = sessionFactory.openSession();

        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(court);
            session.save(court);
            session.flush();
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            ShowText.show(e.getMessage(), parentPane, stage);
        }finally {
            session.close();
        }

        parentPane.setDisable(false);
        stage.close();
    }

    public void keyReleased(KeyEvent event){
        boxForRegion.show();

        if (event.getCode() == KeyCode.ESCAPE && sb.length() > 0) {
            sb.delete(0, sb.length());
        }

        if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.TAB)
            return;

        if (event.getCode() == KeyCode.BACK_SPACE && sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        else
            sb.append(event.getText());

        if(sb.length() == 0) {
            ((ComboBoxListViewSkin) boxForRegion.getSkin()).getListView().scrollTo(0);
            return;
        }

        ObservableList<String> items = boxForRegion.getItems();

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).toLowerCase().startsWith(boxForRegion.getEditor().getText().toLowerCase())){
                ListView<String> lv = ((ComboBoxListViewSkin) boxForRegion.getSkin()).getListView();
                lv.scrollTo(i);
                break;
            }
        }
    }

    @Override
    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setParentPane(Pane parentPane) {
        this.parentPane = parentPane;
    }
}
