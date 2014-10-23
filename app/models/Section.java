package models;


import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Section extends Model{
    @Id
    public Integer id;
    public String name;
    @ManyToOne
    @JoinColumn(name="cycle_id")
    public Cycle cycle;
    @OneToMany(mappedBy = "section")
    public List<Student> students;
    @OneToMany(mappedBy = "section")
    public List<Asignature> asignatures;

    public Section(String name,int cycle_id){
        this.name=name;
        cycle=Cycle.find.byId(cycle_id);
    }
    public static Section create(String name,int cycle_id){
        Section section=new Section(name,cycle_id);
        section.save();
        return section;
    }
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("name",name);
        return map;
    }
    public void update(String name){
        this.name=name;
        update();
    }
    public static Model.Finder<Integer,Section> find = new Finder<>(Integer.class,Section.class);
}
