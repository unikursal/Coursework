package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 28.07.2016.
 */
@Entity
@Table(name="Witness")
public class Witness extends ObjectData {
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public Witness(String fName, String lName, Date dat, String addr, Long numPh) {
        this.fName = fName;
        this.lName = lName;
        this.dat = dat;
        this.addr = addr;
        this.numPh = numPh;
    }

    public Witness(){}

    @Id
    @Column(name="IDw")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDw;

    @Column(name="fName", length = 30)
    private String fName;

    @Column(name="lName", length = 30)
    private String lName;

    @Column(name="dat")
    private Date dat;

    @Column(name="addr", length = 150)
    private String addr;

    @Column(name="numPh")
    private Long numPh;

    @Column(name="idCaus")
    private Integer idCaus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCaus", insertable = false, updatable = false)
    private Cause cause;

    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "TrWitness", joinColumns = @JoinColumn(name = "IDw"), inverseJoinColumns = @JoinColumn(name = "IDtr"))
    Set<Trial> trials = new HashSet<>();

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Ім'я           \t\t\t\t" + lName, "Прізвище       \t\t\t\t" + fName, "Дата народження\t\t\t\t" + dat, "Адрес          \t\t\t\t" + addr, "Номер телефону\t\t\t\t" + numPh );
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

    public Integer getIDw() {
        return IDw;
    }

    public void setIDw(Integer IDw) {
        this.IDw = IDw;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Long getNumPh() {
        return numPh;
    }

    public void setNumPh(Long numPh) {
        this.numPh = numPh;
    }

    public Integer getIdCaus() {
        return idCaus;
    }

    public void setIdCaus(Integer idCaus) {
        this.idCaus = idCaus;
    }

    public Set<Trial> getTrials() {
        return trials;
    }

    public void setTrials(Set<Trial> trials) {
        this.trials = trials;
    }

    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }
}
