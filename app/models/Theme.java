package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Theme extends Model{
    @Id
    public Integer id;
    public String name;
    @ManyToOne
    @JoinColumn(name="course_id")
    public Course course;
    @ManyToMany(mappedBy="themes", cascade = CascadeType.ALL)
    public List<Exam> exams;
    @OneToMany(mappedBy="theme")
    public List<Question> questions;

    public Theme(String name,int course_id){
        this.name=name;
        course=Course.find.byId(course_id);
    }
    public static Theme create(String name,int course_id){
        Theme theme=new Theme(name,course_id);
        theme.save();
        return theme;
    }
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("name",name);
        map.put("course_id",course.id+"");
        return map;
    }
    public void updateName(String name){
        this.name=name;
        update();
    }
    public static Finder<Integer,Theme> find = new Finder<>(Integer.class,Theme.class);
}
