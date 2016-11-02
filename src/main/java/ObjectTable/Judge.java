package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 26.07.2016.
 */
@Entity
@Table(name="Judge")
public class Judge extends ObjectData{
    private final static String nameColumn1 = "Ім'я";
    private final static String nameColumn2 = "Прізвище";

    public Judge(String fName, String lName, Integer experience, Date date, String addr, Long numPh, Integer period, Integer kodCourt) {
        this.fName = fName;
        this.lName = lName;
        this.experience = experience;
        this.date = date;
        this.addr = addr;
        this.numPh = numPh;
        this.period = period;
        this.kodCourt = kodCourt;
    }

    public Judge(){}

    @Id
    @Column(name = "IDj")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDj;

    @Column(name = "fName", length = 30)
    private String fName;

    @Column(name = "lName", length = 30)
    private String lName;

    @Column(name = "posit")
    private Integer posit;

    @Column(name = "experience", length = 2)
    private Integer experience;

    @Formula("YEAR(now()) - YEAR(dat_of_birth)")
    private Integer age;

    @Column(name = "addr", length = 150)
    private String addr;

    @Column(name = "numPh")
    private Long numPh;

    @Column(name = "period", length = 2)
    private Integer period;

    @Column(name = "kodCourt")
    private Integer kodCourt;

    @Column(name = "dat_of_birth")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "posit", insertable = false, updatable = false)
    private Posit pos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodCourt", insertable = false, updatable = false)
    private Court court;

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "CauseJudge", joinColumns = @JoinColumn(name = "IDj"), inverseJoinColumns = @JoinColumn(name = "idCaus"))
    private Set<Cause> causes = new HashSet<>();

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Ім'я\t\t\t\t\t\t\t" + fName, "Прізвище\t\t\t\t\t" + lName, "Посада\t\t\t\t\t" + (pos == null ? "" : pos.getName()), "Стаж\t\t\t\t\t" + experience,
                "Вік\t\t\t\t\t" + age, "Адреса\t\t\t\t\t" + addr, "Номер телефону\t\t\t\t\t" + numPh,"Період на який обрано\t\t\t\t\t" + period, "Код суду\t\t\t\t\t" + kodCourt);
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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Integer getKodCourt() {
        return kodCourt;
    }

    public void setKodCourt(Integer kodCourt) {
        this.kodCourt = kodCourt;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Posit getPosit() {
        return pos;
    }

    public void setPosit(Posit posit) {
        this.pos = posit;
    }

    public int getPos() {
        return posit;
    }

    public void setPos(int pos) {
        this.posit = pos;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Integer getIDj() {
        return IDj;
    }

    public void setIDj(Integer IDj) {
        this.IDj = IDj;
    }

    public Set<Cause> getCauses() {
        return causes;
    }

    public void setCauses(Set<Cause> causes) {
        this.causes = causes;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }
}
