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
@Table(name="Assessor")
public class Assessor extends ObjectData {
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public Assessor(String fName, String lName, Integer age, String addr, Long numPh, Integer kodCourt) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.addr = addr;
        this.numPh = numPh;
        this.kodCourt = kodCourt;
    }

    public Assessor(){}

    @Id
    @Column(name="IDas")
    @GeneratedValue(strategy=GenerationType.AUTO )
    private Integer IDas;

    @Column(name="fName", length = 30)
    private String fName;

    @Column(name="lName", length = 30)
    private String lName;

    @Column(name="age", length = 2)
    private Integer age;

    @Column(name="addr", length = 150)
    private String addr;

    @Column(name="numPh")
    private Long numPh;

    @Column(name="kodCourt")
    private Integer kodCourt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodCourt", insertable = false, updatable = false)
    private Court court;

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Ім'я          \t\t\t\t" + fName, "Прізвище      \t\t\t\t" + lName, "Вік           \t\t\t\t" + age, "Адрес         \t\t\t\t" + addr, "Номер телефону\t\t\t\t" + numPh, "Код суду      \t\t\t\t" + kodCourt);
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

    public Integer getIDas() {
        return IDas;
    }

    public void setIDas(Integer IDas) {
        this.IDas = IDas;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Long getNumPh() {
        return numPh;
    }

    public void setNumPh(Long numPh) {
        this.numPh = numPh;
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
