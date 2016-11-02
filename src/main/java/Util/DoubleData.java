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
public class DoubleData extends ObjectData {
    private Double d;

    public DoubleData(Double d){
        this.d = d;
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
        List<String> list = new ArrayList<>();
        list.add(Double.toString(d));

        return FXCollections.observableArrayList(list);
    }

    @Override
    public StringProperty getValue2() {
        return null;
    }

    @Override
    public StringProperty getValue1() {
        return new SimpleStringProperty(Double.toString(d));
    }
}
