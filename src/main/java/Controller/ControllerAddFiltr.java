package Controller;

import Util.MainReader;
import Util.ShowText;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by User on 14.08.2016.
 */
public class ControllerAddFiltr{
    private AnchorPane parentPane;
    private Stage stage;
    private ControllerRoot controllerRoot;
    private static Map<String, String> mapForTranslAttr = new HashMap<>();

    private final HashMap<String, String> map = new HashMap<String, String>(){{
        put("Більше",">");
        put("Менше","<");
        put("Дорівнює","=");
        put("Містить","like ");
        put("Більше, дорівнює",">=");
        put("Менше, дорівнює","<=");
        put("Знаходиться між", "between");
    }};

    private final HashMap<String, String> mapAggregFunc = new HashMap<String, String>(){{
        put("Знайти середнє значення","AVG");
        put("Знайти суму","SUM");
        put("Знайти максимальне значення","MAX");
        put("Знайти мінімальне значення","MIN");
        put("Підрахувати кількість рядків","Count");

    }};

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ChoiceBox<String> choiceBoxForAttr;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField textField;

    @FXML
    private Button butAdd;

    @FXML
    private ChoiceBox<String> choiceForTable;

    @FXML
    private Button butAddTable;

    @FXML
    private TextField textFBetw;

    @FXML
    private ChoiceBox<String> boxForAttr2;

    @FXML
    private ChoiceBox<String> boxForAction;

    @FXML
    private Button butActionPerf;

    @FXML
    private ChoiceBox<String> boxForAttr3;

    @FXML
    private Button butAddGroup;

    @FXML
    private CheckBox checkBox;

    public void initialize(){
        choiceBoxForAttr.setOnAction(action -> choiceBox.setDisable(false));

        choiceBox.setOnAction(action -> {
            if (choiceBoxForAttr.getValue().contains("Дата"))
                ShowText.show("Формат дати - Число.Місяць.Рік наприклад 01.01.2001 або 01.01.01", anchorPane, stage);

            if (choiceBox.getSelectionModel().getSelectedItem().equals("Знаходиться між"))
                textFBetw.setDisable(false);

            textField.setDisable(false);
        });

        boxForAttr2.setOnAction(event -> boxForAction.setDisable(false));

        boxForAction.setOnAction(event -> butActionPerf.setDisable(false));

        boxForAttr3.setOnAction(event-> butAddGroup.setDisable(false));
    }

    /*
    Зробити перевірку на вміст у тексті символу % при виборі like
     */
    public void addFiltr(MouseEvent event){
        String query = "";
        String valueChoiceBox = choiceBox.getValue();
        String valChBoxForAttr = choiceBoxForAttr.getValue();
        String valueTextF1 = textField.getText();
        String valueTextF2 = textFBetw.getText();

        if(valChBoxForAttr != null) {

            if (valChBoxForAttr.contains("Дата")) {
                valueTextF1 = " str_to_date('" + valueTextF1 + "' , '%d.%m.%Y')";
                valueTextF2 = " str_to_date('" + valueTextF2 + "' , '%d.%m.%Y')";
            }

            query += mapForTranslAttr.get(valChBoxForAttr);
            query += " " + map.get(valueChoiceBox) + " " + (valueChoiceBox.equals("Містить") ? "'%" : "") + valueTextF1 + (valueChoiceBox.equals("Знаходиться між") ? " and " + valueTextF2 : "") + (valueChoiceBox.equals("Містить") ? "%'" : "");

            controllerRoot.setQuery(query, "");


            System.out.println(checkBox.selectedProperty().getValue());
        }
    }

    public void clickAddGroup(){
        String nameAttr =  mapForTranslAttr.get(boxForAttr3.getValue());
        controllerRoot.setTextForGroupBy(" group by " + nameAttr);

        butAddGroup.setDisable(true);
    }

    public void performAct(MouseEvent event){
        controllerRoot.setFlagForAggrFunc(true);

        String quer = mapAggregFunc.get(boxForAction.getValue());
        String nameAttr =  mapForTranslAttr.get(boxForAttr2.getValue());

        quer += "(" + nameAttr + ")";

        controllerRoot.setTextForAggregFunc(quer);

        butActionPerf.setDisable(true);
    }

    public void setItems(List<String> nameTableJoin){
        Map<String, Map<String, String>> map = MainReader.getMapTable(parentPane, stage);
        String nameCurTable = controllerRoot.getNameTableJoin().peekLast();

        if(nameCurTable != null)
            choiceForTable.setItems(FXCollections.observableArrayList(map.get(nameCurTable).keySet()));

        Map<String,String> mapForAttr = null;

        for(String str: nameTableJoin) {
            mapForAttr = MainReader.getMap("/" + str + ".txt", anchorPane, stage);
            Set<String> keySet = mapForAttr.keySet();

            for(String s: keySet) {
                String oldValue = mapForAttr.get(s);
                String newValue = str.toLowerCase().concat("." + oldValue);
                mapForAttr.replace(s, oldValue,newValue);
            }

            mapForTranslAttr.putAll(mapForAttr);

            choiceBoxForAttr.getItems().addAll(keySet);

            boxForAttr2.getItems().addAll(keySet);

            boxForAttr3.getItems().addAll(keySet);
        }
    }

    public void addTable(MouseEvent event){
        if(choiceForTable.getValue() == null)
            return;

        Map<String, Map<String, String>> mapTable = MainReader.getMapTable(parentPane, stage);

        String nameJoinTab =  controllerRoot.getMapForChoiceBox().get(choiceForTable.getValue());

        if(nameJoinTab == null) {
            ShowText.show("nameJoinTab equals null", parentPane, stage);
            return;
        }

        String nameAlias = nameJoinTab.toLowerCase();
        String nameCurTable = controllerRoot.getNameTableJoin().peekLast();

        String tables = controllerRoot.getNameTableJoin().peekLast().toLowerCase() + "." + mapTable.get(nameCurTable).get(choiceForTable.getValue())  + " as " + nameAlias;

        controllerRoot.setQuery("", tables);

        Map<String,String> mapForAttr = MainReader.getMap("/" + nameJoinTab + ".txt", anchorPane, stage);

        Set<String> keySet = mapForAttr.keySet();

        for(String s: keySet) {
            String oldValue = mapForAttr.get(s);
            String newValue = nameAlias.concat("." + oldValue);
            mapForAttr.replace(s, oldValue, newValue);
        }

        mapForTranslAttr.putAll(mapForAttr);

        choiceBoxForAttr.getItems().addAll(keySet);
        boxForAttr2.getItems().addAll(keySet);
        boxForAttr3.getItems().addAll(keySet);

        controllerRoot.addNameTableJoin(nameJoinTab);

        choiceForTable.setItems(FXCollections.observableArrayList(mapTable.get(nameJoinTab).keySet()));
    }

    public void setParentPane(AnchorPane parentPane) {
        this.parentPane = parentPane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControllerRoot(ControllerRoot controllerRoot) {
        this.controllerRoot = controllerRoot;
    }
}
