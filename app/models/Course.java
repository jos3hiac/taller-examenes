package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Course extends Model{
    @Id
    public int id;
    public String code;
    public String name;
    @OneToMany(mappedBy = "course")
    public List<Theme> themes;
    @OneToMany(mappedBy = "course")
    public List<Asignature> asignatures;
    @ManyToMany(mappedBy="courses", cascade = CascadeType.ALL)
    public List<Cycle> cycles;

    public Course(String name){
        this.name=name;
    }
    public static Course create(String name){
        Course course=new Course(name);
        course.save();
        return course;
    }
    public static Model.Finder<Integer,Course> find = new Model.Finder<>(Integer.class,Course.class);

    public List<Theme> themes(int exam_id,int cycle_id){
        SqlQuery query = Ebean.createSqlQuery("select th.id from course co join theme th on (co.id=:id and co.id=th.course_id) " +
                "join exam_x_theme et on (et.theme_id=th.id) join exam e on (e.id=:e_id and e.id=et.exam_id and e.cycle_id=:cy_id)")
                .setParameter("id",id).setParameter("e_id",exam_id).setParameter("cy_id",cycle_id);

        List<SqlRow> rows = query.findList();
        List<Theme> themes=new ArrayList<>();
        for(SqlRow row : rows){
            themes.add(Theme.find.byId(row.getInteger("id")));
        }
        return themes;
    }
    public List<Question> questions(){
        SqlQuery query = Ebean.createSqlQuery("select q.id from course co join theme th on (co.id=:id and co.id=th.course_id)" +
                "join question q on (q.theme_id=th.id)")
                .setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Question> questions=new ArrayList<>();
        for(SqlRow row : rows){
            questions.add(Question.find.byId(row.getInteger("id")));
        }
        return questions;
    }
}
