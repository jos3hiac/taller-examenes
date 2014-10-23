package models;

import controllers.Application;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cycle extends Model{
    @Id
    public int id;
    public String name,duration;
    @Column(name="startdate")
    public Date startDate=new Date();
    @Column(name="lastdate")
    public Date lastDate=new Date();
    @ManyToOne
    @JoinColumn(name="academy_id")
    public Academy academy;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="cycle_x_course", joinColumns={@JoinColumn(name="cycle_id")}, inverseJoinColumns={@JoinColumn(name="course_id")})
    public List<Course> courses;
    @OneToMany(mappedBy = "cycle")
    public List<Section> sections;
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
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("name",name);
        map.put("duration",duration);
        map.put("startDate",Application.getDate(startDate));
        map.put("lastDate",Application.getDate(lastDate));
        return map;
    }
    public void update(String name,String duration,String startDate,String lastDate){
        this.name=name;
        this.duration=duration;
        if(Application.getDate(startDate)!=null)this.startDate=Application.getDate(startDate);
        if(Application.getDate(lastDate)!=null)this.lastDate=Application.getDate(lastDate);
        update();
    }
    public static Model.Finder<Integer,Cycle> find = new Finder<>(Integer.class,Cycle.class);
}
