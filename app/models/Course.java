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
    public static Model.Finder<Integer,Course> find = new Model.Finder<>(Integer.class,Course.class);

    public List<Theme> themes(int exam_id){
        SqlQuery query = Ebean.createSqlQuery("select th.id from course co join theme th on (co.id=:id and co.id=th.course_id) " +
                "join exam_x_theme et on (et.theme_id=th.id) join exam e on (e.id=:e_id and e.id=et.exam_id)")
                .setParameter("id",id).setParameter("e_id",exam_id);

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
    public List<Professor_question> professor_questions(int professor_id,int exam_id){
        SqlQuery query = Ebean.createSqlQuery("select pq.id pq_id from professor_question pq"+
                " join question q on (pq.professor_id=:p_id and pq.exam_id=:e_id and pq.question_id=q.id) join theme th on (q.theme_id=th.id) join course co on(th.course_id=co.id and co.id=:id)")
                .setParameter("p_id",professor_id).setParameter("e_id",exam_id).setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Professor_question> professor_questions=new ArrayList<>();
        for(SqlRow row : rows){
            professor_questions.add(Professor_question.find.byId(row.getInteger("pq_id")));
        }
        return professor_questions;
    }
    public static List<Course> NotInCycle(int cycle_id){
        SqlQuery query = Ebean.createSqlQuery("select c.id cid from course c where c.id not in "+
            "(select course_id from cycle_x_course cc where cc.cycle_id=:id )")
                .setParameter("id",cycle_id);

        List<SqlRow> rows = query.findList();
        List<Course> courses=new ArrayList<>();
        for(SqlRow row : rows){
            courses.add(Course.find.byId(row.getInteger("cid")));
        }
        return courses;
    }
}
