package Util;

import ObjectTable.ObjectData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08.09.2016.
 */
public class LongData extends ObjectData {
    private Long l;

    public LongData(Long l){
        this.l = l;
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
        list.add(Long.toString(l));

        return FXCollections.observableArrayList();
    }

    @Override
    public StringProperty getValue2() {
        return new SimpleStringProperty();
    }

    @Override
    public StringProperty getValue1() {
        return new SimpleStringProperty(Long.toString(l));
    }
}
