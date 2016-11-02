package Controller;

import Util.*;
import ObjectTable.ObjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

/**
 * Created by User on 26.07.2016.
 */
public class ControllerRoot {
    public static MySettings mySettings;

    public static final int defaultKodCourt = 0;

    private Stage prStage;
    private static HashMap<String, String> mapForChoiceBox = new HashMap<>();
    private String valueSelectBox;

    private String textForAggregFunc = "";
    private boolean flagForAggrFunc = false;

    private String textForGroupBy = "";

    private int maxResult = 50;

    private String query = ""; //" limit " + maxResult;
    /*
        Містить список назв усіх таблиць на англійській, які беруть участь у складані фільтра
     */
    private LinkedList<String> nameTableJoin = new LinkedList<>();
    //public final static int lenAlias = 4;

    private List<String> filtrAddNewObject = new ArrayList<String>(){{
        add("Суди");
        add("Працівники суду");
        add("Судді");
        add("Народні засідателі");
        add("Судові рішення");
        add("Судові справи");
        add("Засідання");
        add("Посади");
    }};

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu helpMenu;

    @FXML
    private Menu abautMenu;

    @FXML
    private ScrollPane scrPaneTable;

    @FXML
    private TableView<Object[]> tableView;

    @FXML
    private ScrollPane scrPaneLs;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button butEdit;

    @FXML
    private Button butDel;

    @FXML
    private Button butAdd;

    @FXML
    private Button butFiltr;

    @FXML
    private Button butAddTable;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    MenuItem menuSettings;

    public void initialize(){
        Map<String, String> map = MainReader.getMap("/f1.txt", anchorPane, prStage);
        if(map.isEmpty())
            choiceBox.setDisable(true);
        else
            mapForChoiceBox.putAll(map);

        tableView.setPlaceholder(new Label("Дані відсутні"));
        tableView.getSelectionModel().getSelectedCells().addListener((ListChangeListener.Change<? extends TablePosition> c) -> onChanged(c));

        choiceBox.setOnAction(action -> actionChoiceBox());
    }

    public void clickSearch(){
        initializeMySettings();

        valueSelectBox = choiceBox.getSelectionModel().getSelectedItem();
        if(valueSelectBox == null)
            return;

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == nul", anchorPane, prStage);
            return;
        }

        Session session = sessionFactory.openSession();

        /*
        Видалення необхідне для того щоб під час добавлення таблиці в одному перегляді, воно не залишалось у такому
        стані під час іншого перегляду іншої таблиці
         */
        tableView.getColumns().removeAll(tableView.getColumns());
        tableView.refresh();

        butAdd.setVisible(true);

        String nameTable = mapForChoiceBox.get(choiceBox.getValue());

        String nameSelect = "";

        if(!flagForAggrFunc){
            for(String nam: nameTableJoin)
                nameSelect += (nam.toLowerCase() + ", ");
            nameSelect = nameSelect.substring(0, nameSelect.length() - 2);
        }else
            nameSelect = textForAggregFunc;

        Query quer = null;

        try {
            quer = session.createQuery("select " + nameSelect + " from " + nameTable + " as " + nameTable.toLowerCase() + query + textForGroupBy);
            query = "";

            System.out.println(quer);

        }catch (HibernateException e){
            ShowText.show(e.getMessage(), anchorPane ,prStage);
            query = "";
            nameTableJoin.clear();
            nameTableJoin.add(mapForChoiceBox.get(choiceBox.getSelectionModel().getSelectedItem()));
            textForAggregFunc = "";
            flagForAggrFunc = false;

            textForGroupBy = "";
            return;
        }

        if(quer == null) {
            ShowText.show("Запит не коректний", anchorPane, prStage);
            return;
        }

        ScrollableResults sc = quer.scroll();

        List<Object[]> listForTable = new ArrayList<>();

        while(sc.next())
            listForTable.add(sc.get());

        if(listForTable.isEmpty())
            return;

        /*
        Необхідно реалізувати класи Long Double Integer
         */
        if(flagForAggrFunc)
            getObjectsAfterAggrFunc(listForTable);

        /*
        quer.getReturnTypes() повертає список класів, які отримані у результаті запиту
         */
        int countObject = quer.getReturnTypes().length;


        for(int i = 0; i < countObject; i++){
            int c = i;

            TableColumn<Object[], String> tableColumn1 = new TableColumn<>();
            tableColumn1.setCellValueFactory(cell -> cellValueFactory1(cell.getValue(), c));

            String nameColumn1 = ((ObjectData)(listForTable.get(0)[i]) == null ? null: (((ObjectData)(listForTable.get(0)[i])).getNameColumn1()));
            tableColumn1.setText(nameColumn1);

            tableView.getColumns().add(tableColumn1);


            TableColumn<Object[], String> tableColumn2 = new TableColumn<>();
            tableColumn2.setCellValueFactory(cell -> cellValueFactory2(cell.getValue(), c));

            String nameColumn2 = ((ObjectData)(listForTable.get(0)[i]) == null ? null: (((ObjectData)(listForTable.get(0)[i])).getNameColumn2()));
            tableColumn2.setText(nameColumn2);

            tableView.getColumns().add(tableColumn2);
        }


        tableView.setItems(FXCollections.observableArrayList(listForTable));

        textForAggregFunc = "";
        flagForAggrFunc = false;

        nameTableJoin.clear();
        nameTableJoin.add(mapForChoiceBox.get(choiceBox.getSelectionModel().getSelectedItem()));

        textForGroupBy = "";

        session.close();
    }

    public void actionChoiceBox(){
        query = "";
        nameTableJoin.clear();
        nameTableJoin.add(mapForChoiceBox.get(choiceBox.getValue()));

        boolean b = false;
        String nameTable = choiceBox.getValue();

        for(String s: filtrAddNewObject)
            if(s.equals(nameTable)) {
                b = true;
                break;
            }
        if(b)
            butAdd.setVisible(true);
        else
            butAdd.setVisible(false);
    }

    private void onChanged(ListChangeListener.Change<? extends TablePosition> c){
        Object[] objects = tableView.getSelectionModel().getSelectedItem();
        ObservableList<String> selectedItem = FXCollections.observableArrayList();

        if(objects == null)
            return;

        for(Object object: objects)
            selectedItem.addAll( object == null ? FXCollections.observableArrayList() : ((ObjectData)object).getObservableList());

        if (selectedItem != null) {
            listView.setItems(selectedItem);

            butDel.setVisible(true);
            butEdit.setVisible(true);
        }else{
            listView.setItems(FXCollections.observableArrayList());

            butDel.setVisible(false);
            butEdit.setVisible(false);
        }
    }

    @FXML
    public void clickedEdit(MouseEvent event){

    }

    @FXML
    public void clickAddFiltr(MouseEvent event){
        if(choiceBox.getValue() == null)
            return;

        FXMLLoader loader = new FXMLLoader();

        String nameFile = "/filtr.fxml";
        loader.setLocation(getClass().getResource(nameFile));
        if(loader.getLocation() == null){
            ShowText.show("Не знайдено розташування " + nameFile, anchorPane, prStage);
            return;
        }

        Stage stage = new Stage();
        try {
            Parent root = loader.load();

            ControllerAddFiltr controller = loader.getController();
            controller.setControllerRoot(this);
            controller.setStage(stage);
            controller.setParentPane(anchorPane);
            controller.setItems(nameTableJoin);

            stage.setScene(new Scene(root));
            stage.initOwner(prStage);
            stage.show();
            stage.setOnCloseRequest((WindowEvent e) -> anchorPane.setDisable(false));
            anchorPane.setDisable(true);
        }catch (IOException e){
            ShowText.show(e.getMessage(), anchorPane, prStage);
            return;
        }
    }

    @FXML
    public void clickedDel(MouseEvent event){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        if(sessionFactory == null){
            ShowText.show("Помилка, sessionFactory == nul", anchorPane, prStage);
            return;
        }

        Session session = sessionFactory.openSession();


        Object[] objects = tableView.getSelectionModel().getSelectedItem();

        if(objects.length == 1) {
            ObjectData objectData = (ObjectData)objects[0];
            Transaction transaction = session.beginTransaction();
            session.delete(objectData);
            transaction.commit();
            tableView.getItems().remove(objectData);
            tableView.refresh();
        }

        session.close();
    }

    @FXML
    public void clickAdd(MouseEvent event){
        if(choiceBox.getSelectionModel().getSelectedItem() == null)
            return;
        Stage stage = new Stage();
        stage.initOwner(prStage);
        FXMLLoader loader = new FXMLLoader();
        String nameFile = "/" + mapForChoiceBox.get(choiceBox.getSelectionModel().getSelectedItem()) + ".fxml";
        loader.setLocation(getClass().getResource(nameFile));

        if(loader.getLocation() == null){
            Popup popup = new Popup();
            popup.setX(event.getScreenX());
            popup.setY(event.getScreenY());
            popup.setAutoHide(true);
            Label label = new Label("Не знайдено файл " + nameFile ) ;
            label.setStyle("-fx-border-color: darkSlateGrey; -fx-border-witch: 3pt; -fx-font: 12pt Arial; -fx-text-fill: darkSlateBlue");
            popup.getContent().add(label);
            popup.show(prStage);
            return;
        }
        try {
            Parent root = loader.load();

            Controller controller = loader.getController();
            controller.setTableView(tableView);
            controller.setStage(stage);
            controller.setParentPane(anchorPane);

            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest((WindowEvent e) -> anchorPane.setDisable(false));
            anchorPane.setDisable(true);
        }catch (IOException e){
            ShowText.show(e.getMessage(), anchorPane, prStage);
        }
    }

    private StringProperty cellValueFactory1(Object[] objects, int i){
        if(objects[i] != null)
            return ((ObjectData) objects[i]).getValue1();
        return new SimpleStringProperty();
    }

    private StringProperty cellValueFactory2(Object[] objects, int i){
        if(objects[i] != null)
            return ((ObjectData)objects[i]).getValue2();
        return new SimpleStringProperty();
    }

    private void getObjectsAfterAggrFunc(List<Object[]> listForTable){
        List<Object[]> resultList = new ArrayList<>();

        for(Object[] objects: listForTable) {
            List<Object> datas = new ArrayList<Object>();
            for (Object o : objects) {
                if (o instanceof Long) {
                    LongData longData = new LongData((Long) o);
                    datas.add(longData);
                    continue;
                }
                if (o instanceof Integer) {
                    IntegerData integerData = new IntegerData((Integer) o);
                    datas.add(integerData);
                    continue;
                }
                if (o instanceof Double) {
                    DoubleData doubleData = new DoubleData((Double) o);
                    datas.add(doubleData);
                    continue;
                }
                datas.add(o);
            }
            resultList.add(datas.toArray());
        }

        listForTable.clear();

        listForTable.addAll(resultList);
    }

    public void setPrStage(Stage prStage) {
        this.prStage = prStage;
    }

    public static HashMap<String, String> getMapForChoiceBox() {
        return mapForChoiceBox;
    }

    public void addNameTableJoin(String str){
        nameTableJoin.add(str);
    }

    public LinkedList<String> getNameTableJoin(){
        return nameTableJoin;
    }

    public void setQuery(String q, String table){
        if(query.isEmpty())
            query = ( table.isEmpty() ? "" : " left join " + table) + (q.isEmpty() ? "" : " where " + q );
        else{
            boolean contains = query.contains("where");
            if(contains){
                String phrase[] = query.split("where");
                query = phrase[0] + ( table.isEmpty() ? "" : " left join " + table) + " where " + phrase[1] + (q.isEmpty() ? "" : " and " + q);
            }else{
                query += (table.isEmpty() ? "" : " left join " + table) + (q.isEmpty() ? "" : " where " + q);
            }

        }
    }

    public void onActionSettings(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/settings.fxml"));
            Parent pane = loader.load();
            Stage stage = new Stage();
            ControllerSettings controllerSettings =  loader.getController();
            controllerSettings.setStage(stage);
            stage.setScene(new Scene(pane));
            stage.show();
        }catch (IOException e){
            ShowText.show(e.getMessage(), anchorPane, prStage);
        }
    }

    public void setTextForAggregFunc(String textForAggregFunc) {
        this.textForAggregFunc = textForAggregFunc;
    }

    public void setFlagForAggrFunc(boolean b){
        flagForAggrFunc = b;
    }

    public void setTextForGroupBy(String text){
        textForGroupBy = text;
    }

    private void initializeMySettings(){
        try {
            JAXBContext context = JAXBContext.newInstance(MySettings.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            MySettings mySettingsegs;
            mySettingsegs = (MySettings) unmarshaller.unmarshal( getClass().getResourceAsStream("/settings.xml"));
        }catch (JAXBException e) {
            ShowText.show("Не вдалось завантажити початкові налаштування\n" + e.getMessage(), anchorPane, null);
            prStage.close();
        }
    }
}
