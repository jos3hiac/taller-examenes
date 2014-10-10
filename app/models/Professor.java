package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Professor extends Model{
    @Id
    public int id;
    public String code;
    public String name;
    public String lastname;
    @OneToOne
    @JoinColumn(name="user_id")
    public User user;
    @OneToMany(mappedBy = "professor")
    public List<Asignature> asignatures;
    @OneToMany(mappedBy = "professor")
    public List<Professor_question> professor_questions;

    public Professor(String name,String lastname,int user_id){
        this.name=name;
        this.lastname=lastname;
        user=User.find.byId(user_id);
    }
    public static Professor create(String name,String lastname,int user_id){
        Professor professor=new Professor(name,lastname,user_id);
        professor.save();
        return professor;
    }
    public static Finder<Integer,Professor> find = new Finder<>(Integer.class,Professor.class);

    public List<Cycle> cycles(){
        SqlQuery query = Ebean.createSqlQuery("select c.id cid from professor p join asignature a on (p.id=:id and p.id=a.professor_id) " +
                "join section s on (a.section_id=s.id) join cycle c on (s.cycle_id=c.id) group by(c.id)")
                .setParameter("id",id);

        List<SqlRow> rows = query.findList();
        List<Cycle> cycles=new ArrayList<>();
        for(SqlRow row : rows){
            cycles.add(Cycle.find.byId(row.getInteger("cid")));
        }
        return cycles;
    }
    public List<Course> courses(int cycle_id){
        SqlQuery query = Ebean.createSqlQuery("select co.id co_id from professor p join asignature a on (p.id=:id and p.id=a.professor_id) " +
                "join section s on (a.section_id=s.id and s.cycle_id=:cy_id) join course co on (co.id=a.course_id) group by(co.id)")
                .setParameter("id",id).setParameter("cy_id",cycle_id);

        List<SqlRow> rows = query.findList();
        List<Course> courses=new ArrayList<>();
        for(SqlRow row : rows){
            courses.add(Course.find.byId(row.getInteger("co_id")));
        }
        return courses;
    }
}
