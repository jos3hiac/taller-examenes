package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Student extends Model{
    @Id
    public Integer id;
    public String code;
    public String name;
    public String lastname;
    @OneToOne
    @JoinColumn(name="user_id")
    public User user;
    @ManyToOne
    @JoinColumn(name="section_id")
    public Section section;
    public Student(String name,String lastname,int user_id){
        this.name=name;
        this.lastname=lastname;
        user=User.find.byId(user_id);
    }
    public static Student create(String name,String lastname,int user_id){
        Student student =new Student(name,lastname,user_id);
        student.save();
        return student;
    }
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("name",name);
        map.put("lastname",lastname);
        map.put("user_id",user.id+"");
        map.put("section_id",section.id+"");
        return map;
    }
    public void updateSection(int section_id){
        section=Section.find.byId(section_id);
        update();
    }
    public void update(String name,String lastname){
        this.name=name;
        this.lastname=lastname;
        update();
    }
    public static Finder<Integer,Student> find = new Finder<>(Integer.class,Student.class);
}
