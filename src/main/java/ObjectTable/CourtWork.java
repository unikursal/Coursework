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
@Table(name="CourtWork")
public class CourtWork extends ObjectData {
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public CourtWork(String fName, String lName, String posit, Integer kodCourt) {
        this.fName = fName;
        this.lName = lName;
        this.posit = posit;
        this.kodCourt = kodCourt;
    }

    public CourtWork(){}

    @Id
    @Column(name="IDcw")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer IDcw;

    @Column(name="fName", length = 30)
    String fName;

    @Column(name="lName", length = 30)
    String lName;

    @Column(name="posit", length = 32)
    String posit;

    @Column(name="kodCourt", length = 6)
    Integer kodCourt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodCourt", insertable = false, updatable = false)
    private Court court;

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Ім'я    \t\t\t\t" + fName, "Прізвище\t\t\t\t" + lName, "Посада  \t\t\t\t" + posit, "Код суду\t\t\t\t" + kodCourt);
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
    public String getNameColumn2() {
        return nameColumn2;
    }

    @Override
    public String getNameColumn1() {
        return nameColumn1;
    }

    public Integer getIDcw() {
        return IDcw;
    }

    public void setIDcw(Integer IDcw) {
        this.IDcw = IDcw;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPosit() {
        return posit;
    }

    public void setPosit(String posit) {
        this.posit = posit;
    }

    public Integer getKodCourt() {
        return kodCourt;
    }

    public void setKodCourt(Integer kodCourt) {
        this.kodCourt = kodCourt;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }
}
