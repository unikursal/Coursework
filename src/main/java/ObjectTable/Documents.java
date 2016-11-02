package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;

/**
 * Created by User on 28.07.2016.
 */
@Entity
@Table(name="Documents")
public class Documents extends ObjectData {
    private final static String nameColumn1 = "Номер";
    private final static String nameColumn2 = "Опис";

    public Documents( Integer num, String descr) {
        this.descr = descr;
        this.num = num;
    }

    public Documents(){}

    @Id
    @Column(name="num")
    Integer num;

    @Column(name="descr", length = 4000)
    String descr;

    @Column(name="idCaus")
    Integer idCaus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCaus", insertable = false, updatable = false)
    private Cause cause;

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Номер       \t\t\t\t" + num, "Опис        \t\t\t\t" + "зробити опис");
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(Integer.toString(num));
    }

    @Override
    public StringProperty getValue2(){
        return new SimpleStringProperty("Зробити опис");
    }

    @Override
    public String getNameColumn1() {
        return nameColumn1;
    }

    @Override
    public String getNameColumn2() {
        return nameColumn2;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getIdCaus() {
        return idCaus;
    }

    public void setIdCaus(Integer idCaus) {
        this.idCaus = idCaus;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }
}
