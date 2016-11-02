package ObjectTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 07.10.2016.
 */
@Entity
@Table(name = "posit")
public class Posit extends ObjectData {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", length = 30)
    private String name;

    @OneToMany(mappedBy = "posit")
    private Set<Judge> judgeSet = new HashSet<Judge>();

    public Posit(){}

    public Posit(String nam){
        this.name = nam;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Judge> getJudgeSet() {
        return judgeSet;
    }

    public void setJudgeSet(Set<Judge> judgeSet) {
        this.judgeSet = judgeSet;
    }

    @Override
    public String getNameColumn1() {
        return "Назва";
    }

    @Override
    public String getNameColumn2() {
        return "";
    }

    @Override
    public ObservableList<String> getObservableList() {
        return FXCollections.observableArrayList("Назва          \t\t\t\t" + name);
    }

    @Override
    public StringProperty getValue1() {
        return new SimpleStringProperty(name);
    }

    @Override
    public StringProperty getValue2() {
        return null;
    }
}
