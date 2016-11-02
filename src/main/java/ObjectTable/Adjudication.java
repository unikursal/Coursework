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
@Table(name="Adjudication")
public class Adjudication extends ObjectData {
    private final static String nameColumn1 = "Номер";
    private final static String nameColumn2 = "Текст";

    public Adjudication(Integer num, Date dat, String forma, String text, Integer idCaus) {
        this.num = num;
        this.dat = dat;
        this.forma = forma;
        this.text = text;
        this.idCaus = idCaus;
    }

    public Adjudication(){}

    @Id
    @Column(name="num")
    private Integer num;

    @Column(name="dat")
    private Date dat;

    @Column(name="forma", length = 24)
    private String forma;

    @Column(name="text", length = 8000)
    private String text;

    @Column(name="idCaus")
    private Integer idCaus;

    @OneToOne
    @JoinColumn(name = "idCaus", insertable = false, updatable = false)
    private Cause cause;

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Номер         \t\t\t\t" +  num , "Дата ухвалення\t\t\t\t" + dat, "Форма         \t\t\t\t" + forma, "Текст         \t\t\t\t" + "Зробити обробку тексту");
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(Integer.toString(num));
    }

    @Override
    public StringProperty getValue2(){
        return new SimpleStringProperty("Придумати опис");
    }

    @Override
    public String getNameColumn1(){
        return nameColumn1;
    }

    @Override
    public String getNameColumn2(){
        return nameColumn2;
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

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

