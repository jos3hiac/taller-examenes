package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Section extends Model{
    @Id
    public Integer id;
    @ManyToOne
    @JoinColumn(name="cycle_id")
    public Cycle cycle;
    @OneToMany(mappedBy = "section")
    public List<Student> students;
    @OneToMany(mappedBy = "section")
    public List<Asignature> asignatures;
    public static Finder<Integer,Section> find = new Finder<>(Integer.class,Section.class);
}
