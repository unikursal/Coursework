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
@Table(name="Trial")
public class Trial extends ObjectData {
    private final static String nameColumn1 = "Дата";
    private final static String nameColumn2 = "Номер зали засідань";

    public Trial(Date dat, Integer numRoom, Integer kodCourt) {
        this.dat = dat;
        this.numRoom = numRoom;
        this.kodCourt = kodCourt;
    }

    public Trial(){}

    @Id
    @Column(name="IDtr")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDtr;

    @Column(name="dat")
    private Date dat;

    @Column(name="numRoom", length = 2)
    private Integer numRoom;

    @Column(name="idCaus")
    private Integer idCaus;

    @Column(name="kodCourt", length = 6)
    private Integer kodCourt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodCourt", insertable = false, updatable = false)
    private Court court;

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "TrWitness", joinColumns = @JoinColumn(name = "IDtr"), inverseJoinColumns = @JoinColumn(name = "IDw"))
    Set<Witness> witnessSet = new HashSet<>();

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Дата               \t\t\t\t" + dat, "Номер зали засідань\t\t\t\t" + numRoom, "Код суду    \t\t\t\t" + kodCourt);
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(dat.toString());
    }

    @Override
    public StringProperty getValue2(){
        return new SimpleStringProperty(Integer.toString(numRoom));
    }

    @Override
    public String getNameColumn1() {
        return nameColumn1;
    }

    @Override
    public String getNameColumn2() {
        return nameColumn2;
    }

    public Set<Witness> getWitnessSet() {
        return witnessSet;
    }

    public void setWitnessSet(Set<Witness> witnessSet) {
        this.witnessSet = witnessSet;
    }

    public Integer getIDtr() {
        return IDtr;
    }

    public void setIDtr(Integer IDtr) {
        this.IDtr = IDtr;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public Integer getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(Integer numRoom) {
        this.numRoom = numRoom;
    }

    public Integer getKodCourt() {
        return kodCourt;
    }

    public void setKodCourt(Integer kodCourt) {
        this.kodCourt = kodCourt;
    }

    public Integer getIdCaus() {
        return idCaus;
    }

    public void setIdCause(Integer idCaus) {
        this.idCaus = idCaus;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }
}
