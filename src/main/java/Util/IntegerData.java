package Util;

import ObjectTable.ObjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 09.09.2016.
 */
public class IntegerData extends ObjectData {
    private Integer integer;

    public IntegerData(Integer i){
        integer = i;
    }

    @Override
    public String getNameColumn1() {
        return null;
    }

    @Override
    public String getNameColumn2() {
        return null;
    }

    @Override
    public ObservableList<String> getObservableList() {
        List<String> list = new ArrayList<String>();
        list.add(Integer.toString(integer));

        return FXCollections.observableArrayList(list);
    }

    @Override
    public StringProperty getValue2() {
        return null;
    }

    @Override
    public StringProperty getValue1() {
        return new SimpleStringProperty(Integer.toString(integer));
    }
}
