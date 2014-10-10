package models;

import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Exam extends Model{
    @Id
    public int id;
    //@Formats.DateTime(pattern="dd/MM/yyyy")
    public Date date = new Date();
    @ManyToOne
    @JoinColumn(name="cycle_id")
    public Cycle cycle;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="exam_x_theme", joinColumns={@JoinColumn(name="exam_id")}, inverseJoinColumns={@JoinColumn(name="theme_id")})
    public List<Theme> themes;
    @OneToMany(mappedBy = "exam")
    public List<Professor_question> professor_questions;

    public Exam(Date date,int cycle_id){
        this.date=date;
        cycle=Cycle.find.byId(cycle_id);
    }
    public static Exam create(Date date,int cycle_id){
        Exam exam=new Exam(date,cycle_id);
        exam.save();
        return exam;
    }
    public static Finder<Integer,Exam> find = new Finder<>(Integer.class,Exam.class);

}
