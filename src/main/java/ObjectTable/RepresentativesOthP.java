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
@Table(name="RepresentativesOthP")
public class RepresentativesOthP extends ObjectData {
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public RepresentativesOthP(String fName, String lName, String status, String addr, Integer IDothp) {
        this.fName = fName;
        this.lName = lName;
        this.status = status;
        this.addr = addr;
        this.IDothp = IDothp;
    }

    public RepresentativesOthP(){}

    @Id
    @Column(name="IDr")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDr;

    @Column(name="fName", length = 30)
    private String fName;

    @Column(name="lName", length = 30)
    private String lName;

    @Column(name="status", length = 30)
    private String status;

    @Column(name="addr", length = 150)
    private String addr;

    @Column(name="IDothp")
    private Integer IDothp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDothp", insertable = false, updatable = false)
    private OtherParty otherParty;

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Ім'я          \t\t\t\t" + fName, "Прізвище      \t\t\t\t" + lName, "Статус        \t\t\t\t" + status, "Адреса        \t\t\t\t" + addr);
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(fName);
    }

    @Override
    public StringProperty getValue2(){
        return new SimpleStringProperty(lName);
    }

    @Override
    public String getNameColumn1() {
        return nameColumn1;
    }

    @Override
    public String getNameColumn2() {
        return nameColumn2;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Integer getIDr() {
        return IDr;
    }

    public void setIDr(Integer IDr) {
        this.IDr = IDr;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getIDothp() {
        return IDothp;
    }

    public void setIDothp(Integer IDothp) {
        this.IDothp = IDothp;
    }

    public OtherParty getOtherParty() {
        return otherParty;
    }

    public void setOtherParty(OtherParty otherParty) {
        this.otherParty = otherParty;
    }
}
