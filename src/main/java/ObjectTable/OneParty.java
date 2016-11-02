package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 28.07.2016.
 */
@Entity
@Table(name="Oneparty")
public class OneParty extends ObjectData {
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public OneParty(String fName, String lName, String status, Long numPh, String addr) {
        this.fName = fName;
        this.lName = lName;
        this.status = status;
        this.numPh = numPh;
        this.addr = addr;
    }

    public OneParty(){}

    @Id
    @Column(name="IDop")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDop;

    @Column(name="fName", length = 30)
    private String fName;

    @Column(name="lName", length = 30)
    private String lName;

    @Column(name="status", length = 20)
    private String status;

    @Column(name="numPh")
    private Long numPh;

    @Column(name="addr", length = 150)
    private String addr;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "IDop")
    Set<RepresentativesOnP> representativesOnPs = new HashSet<>();

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Ім'я          \t\t\t\t" + fName, "Прізвище      \t\t\t\t" + lName , "Статус        \t\t\t\t" + status, "Номер телефону\t\t\t\t" + numPh, "Адреса        \t\t\t\t" + addr);
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

    public Integer getIDop() {
        return IDop;
    }

    public void setIDop(Integer IDop) {
        this.IDop = IDop;
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

    public Long getNumPh() {
        return numPh;
    }

    public void setNumPh(Long numPh) {
        this.numPh = numPh;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Set<RepresentativesOnP> getRepresentativesOnPs() {
        return representativesOnPs;
    }

    public void setRepresentativesOnPs(Set<RepresentativesOnP> representativesOnPs) {
        this.representativesOnPs = representativesOnPs;
    }
}
