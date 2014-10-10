package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cycle extends Model{
    @Id
    public int id;
    public String name;
    @ManyToOne
    @JoinColumn(name="academy_id")
    public Academy academy;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="cycle_x_course", joinColumns={@JoinColumn(name="cycle_id")}, inverseJoinColumns={@JoinColumn(name="course_id")})
    public List<Course> courses;
    @OneToMany(mappedBy = "cycle")
    public List<Section> section;
    @OneToMany(mappedBy = "cycle")
    public List<Exam> exams;
    public Cycle(String name,int academy_id){
        this.name=name;
        academy=Academy.find.byId(academy_id);
    }
    public static Cycle create(String name,int academy_id){
        Cycle cycle=new Cycle(name,academy_id);
        cycle.save();
        return cycle;
    }
    public static Finder<Integer,Cycle> find = new Finder<>(Integer.class,Cycle.class);
}
