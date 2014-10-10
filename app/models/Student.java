package models;

import play.db.ebean.Model;

import javax.persistence.*;

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
    public Student(String name,String lastname,int user_id,int section_id){
        this.name=name;
        this.lastname=lastname;
        user=User.find.byId(user_id);
        section=Section.find.byId(section_id);
    }
    public static Student create(String name,String lastname,int user_id,int section_id){
        Student student =new Student(name,lastname,user_id,section_id);
        student.save();
        return student;
    }
    public static Finder<Integer,Student> find = new Finder<>(Integer.class,Student.class);
}
