package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 26.07.2016.
 */

@Entity
@Table(name="Court")
public class Court extends ObjectData{
    private final static String nameColumn1 = "Код";
    private final static String nameColumn2 = "Назва";

    public Court(Integer kod, String name, String region, String instance, String addr) {
        this.kod = kod;
        this.name = name;
        this.region = region;
        this.instance = instance;
        this.addr = addr;
    }

    public Court() {}

    @Id
    @Column(name = "kod", length = 6)
    private Integer kod;

    @Column(name = "name", length = 65)
    private String name;

    @Column(name = "region", length = 28)
    private String region;

    @Column(name = "instance", length = 12)
    private String instance;

    @Column(name =  "addr", length = 150)
    String addr;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kodCourt")
    private Set<Cause> causeSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kodCourt")
    private Set<Judge> judges = new HashSet<Judge>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kodCourt")
    private Set<Assessor> assessors = new HashSet<Assessor>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kodCourt")
    private Set<CourtWork> courtWorks = new HashSet<CourtWork>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "kodCourt")
    private  Set<Trial> trials = new HashSet<>();

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Код      \t\t\t\t" + kod, "Назва    \t\t\t\t" + name, "Регіон   \t\t\t\t" + region, "Інстанція\t\t\t\t" + instance, "Адрес    \t\t\t\t" + addr);
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(Integer.toString(kod));
    }

    @Override
    public StringProperty getValue2(){
        return new SimpleStringProperty(name);
    }

    @Override
    public String getNameColumn1() {
        return nameColumn1;
    }

    @Override
    public String getNameColumn2() {
        return nameColumn2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKod() {
        return kod;
    }

    public void setKod(Integer kod) {
        this.kod = kod;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public Set<Judge> getJudges() {
        return judges;
    }

    public void setJudges(Set<Judge> judges) {
        this.judges = judges;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Set<Assessor> getAssessors() {
        return assessors;
    }

    public void setAssessors(Set<Assessor> assessors) {
        this.assessors = assessors;
    }

    public Set<CourtWork> getCourtWorks() {
        return courtWorks;
    }

    public void setCourtWorks(Set<CourtWork> courtWorks) {
        this.courtWorks = courtWorks;
    }

    public Set<Trial> getTrials() {
        return trials;
    }

    public void setTrials(Set<Trial> trials) {
        this.trials = trials;
    }

    public Set<Cause> getCauseSet() {
        return causeSet;
    }

    public void setCauseSet(Set<Cause> causeSet) {
        this.causeSet = causeSet;
    }
}
