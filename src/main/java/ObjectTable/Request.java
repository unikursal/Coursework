package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by User on 28.07.2016.
 */
@Entity
@Table(name="Request")
public class Request extends ObjectData {
    private final static String nameColumn1 = "Номер";
    private final static String nameColumn2 = "Опис";

    public Request(Integer num, String descr, Date dat, Date dateAnsw) {
        this.num = num;
        this.descr = descr;
        this.dat = dat;
        this.dateAnsw = dateAnsw;
    }

    public Request(){}

    @Id
    @Column(name="num")
    private Integer num;

    @Column(name="descr", length = 4000)
    private String descr;

    @Column(name="dat")
    private Date dat;

    @Column(name="dateAnsw")
    private Date dateAnsw;

    @Column(name="idCaus")
    private Integer idCaus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCaus", updatable = false, insertable = false)
    private Cause cause;

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Номер\t\t\t\t" + num, "Опис=\t\t\t\t" + descr, "Дата відправлення\t\t\t\t" + dat, "Дата відповіді\t\t\t\t" + dateAnsw);
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Date getDateAnsw() {
        return dateAnsw;
    }

    public void setDateAnsw(Date dateAnsw) {
        this.dateAnsw = dateAnsw;
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
