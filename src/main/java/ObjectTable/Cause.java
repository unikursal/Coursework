package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 28.07.2016.
 */
@Entity
@Table(name="Cause")
public class Cause extends ObjectData {
    private final static String nameColumn1 = "Номер";
    private final static String nameColumn2 = "Опис";

    public static final HashMap<Integer, String> mapsForFormCaus = new HashMap<Integer, String>(){{
        put(1, "Адміністративне");
        put(2, "Кримінальне");
        put(3, "Цивільне");
        put(4, "Справи про адміністративні правопорушення");
    }};

    public static final HashMap<Integer, String> mapsForCategCaus = new HashMap<Integer, String>(){{
        put(1, "Адміністративні справи");
        put(2, "Кримінальні справи");
        put(3, "Невідкладні судові розгляди");
        put(4, "Цивільні справи");
        put(5, "Справи про адміністративні правопорушення");

    }};

    public Cause(){}

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="form", length = 1)
    private Integer form;

    @Column(name="categor", length = 1)
    private Integer categor;

    @Column(name="dat")
    private Date dat;

    @Column(name="descr")
    private String descr;

    @Column(name = "num")
    private String num;

    @Column(name = "kodCourt")
    private Integer kodCourt;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
            @JoinTable(name = "CauseJudge", joinColumns = @JoinColumn(name = "idCaus"), inverseJoinColumns = @JoinColumn(name = "IDj"))
    private Set<Judge> judgeSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
            @JoinTable(name = "CauseAsses", joinColumns = @JoinColumn(name = "idCaus"), inverseJoinColumns = @JoinColumn(name = "IDas"))
    private Set<Assessor> assessorSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
            @JoinTable(name = "causeCourW", joinColumns = @JoinColumn(name = "idCaus"), inverseJoinColumns = @JoinColumn(name = "IDcw"))
    private Set<CourtWork> courtWorkSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "OnParCause", joinColumns = @JoinColumn(name = "idCaus"), inverseJoinColumns = @JoinColumn(name = "IDop"))
    private Set<OneParty> onePartySet = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
            @JoinTable(name = "OthParCause", joinColumns = @JoinColumn(name = "idCaus"), inverseJoinColumns = @JoinColumn(name = "IDothp"))
    private Set<OtherParty> otherPartySet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodCourt", insertable = false, updatable = false)
    private Court court;

    @OneToOne(mappedBy = "cause")
    private Adjudication adjudication;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCaus")
    private Set<Documents> documentsSet = new HashSet<Documents>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCaus")
    private Set<Evidence>  evidenceSet = new HashSet<Evidence>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCaus")
    private Set<Request> requestSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCaus")
    private Set<Trial> trialSet = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCaus")
    private Set<Witness> witnessSet = new HashSet<>();

    public Adjudication getAdjudication() {
        return adjudication;
    }

    public void setAdjudication(Adjudication adjudication) {
        this.adjudication = adjudication;
    }

    @Override
    public ObservableList<String> getObservableList(){
        return FXCollections.observableArrayList("Номер\t\t\t\t" + num, "Форма\t\t\t\t" + mapsForFormCaus.get(form), "Категорія\t\t\t\t" + mapsForCategCaus.get(categor), "Дата\t\t\t\t" + dat, "Опис\t\t\t\t" + descr);
    }

    @Override
    public StringProperty getValue1(){
        return new SimpleStringProperty(num);
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

    public void setParametr(String num, Integer form, Integer categor, Date dat, String descr) {
        this.num = num;
        this.form = form;
        this.categor = categor;
        this.dat = dat;
        this.descr = descr;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategor() {
        return categor;
    }

    public void setCategor(Integer categor) {
        this.categor = categor;
    }

    public Date getDat() {
        return dat;
    }

    public void setDat(Date dat) {
        this.dat = dat;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Set<Judge> getJudgeSet() {
        return judgeSet;
    }

    public void setJudgeSet(Set<Judge> judgeSet) {
        this.judgeSet = judgeSet;
    }

    public Set<Assessor> getAssessorSet() {
        return assessorSet;
    }

    public void setAssessorSet(Set<Assessor> assessorSet) {
        this.assessorSet = assessorSet;
    }

    public Set<CourtWork> getCourtWorkSet() {
        return courtWorkSet;
    }

    public void setCourtWorkSet(Set<CourtWork> courtWorkSet) {
        this.courtWorkSet = courtWorkSet;
    }

    public Set<OneParty> getOnePartySet() {
        return onePartySet;
    }

    public void setOnePartySet(Set<OneParty> onePartySet) {
        this.onePartySet = onePartySet;
    }

    public Set<OtherParty> getOtherPartySet() {
        return otherPartySet;
    }

    public void setOtherPartySet(Set<OtherParty> otherPartySet) {
        this.otherPartySet = otherPartySet;
    }

    public Set<Documents> getDocumentsSet() {
        return documentsSet;
    }

    public void setDocumentsSet(Set<Documents> documentsSet) {
        this.documentsSet = documentsSet;
    }

    public Set<Evidence> getEvidenceSet() {
        return evidenceSet;
    }

    public void setEvidenceSet(Set<Evidence> evidenceSet) {
        this.evidenceSet = evidenceSet;
    }

    public Set<Request> getRequestSet() {
        return requestSet;
    }

    public void setRequestSet(Set<Request> requestSet) {
        this.requestSet = requestSet;
    }

    public Set<Trial> getTrialSet() {
        return trialSet;
    }

    public void setTrialSet(Set<Trial> trialSet) {
        this.trialSet = trialSet;
    }

    public Set<Witness> getWitnessSet() {
        return witnessSet;
    }

    public void setWitnessSet(Set<Witness> witnessSet) {
        this.witnessSet = witnessSet;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Integer getKodCourt() {
        return kodCourt;
    }

    public void setKodCourt(Integer kodCourt) {
        this.kodCourt = kodCourt;
    }

    public void addWitness(Witness witness){
        witnessSet.add(witness);
    }

    public void addRequest(Request request){
        requestSet.add(request);
    }

    public void addOtherParty(OtherParty otherParty){
        otherPartySet.add(otherParty);
    }

    public void addOneParty(OneParty oneParty){
        onePartySet.add(oneParty);
    }

    public void addDocuments(Documents documents){
        documentsSet.add(documents);
    }

    public void addEvidence(Evidence evidence){
        evidenceSet.add(evidence);
    }
}
