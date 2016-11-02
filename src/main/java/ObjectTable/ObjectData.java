package ObjectTable;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 27.07.2016.
 */
public abstract class ObjectData {

    public abstract String getNameColumn1();

    public abstract String getNameColumn2();

    public abstract ObservableList<String> getObservableList();

    public abstract StringProperty getValue2();

    public abstract StringProperty getValue1();

}
