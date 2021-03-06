package models;

import com.avaje.ebean.*;
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
    public void update(String name){
        this.name=name;
        update();
    }
    public static Finder<Integer,Theme> find = new Finder<>(Integer.class,Theme.class);

    public static List<Theme> NotInExam(int exam_id,int course_id){
        SqlQuery query = Ebean.createSqlQuery("select t.id tid from theme t where t.course_id=:cid and t.id not in " +
                "(select theme_id from exam_x_theme et where et.exam_id=:eid )")
                .setParameter("cid",course_id).setParameter("eid",exam_id);

        List<SqlRow> rows = query.findList();
        List<Theme> themes=new ArrayList<>();
        for(SqlRow row : rows){
            themes.add(Theme.find.byId(row.getInteger("tid")));
        }
        return themes;
    }
    public List<Professor_question> professor_questions(int professor_id,int exam_id){
        SqlQuery query = Ebean.createSqlQuery("select pq.id pq_id from professor_question pq"+
                " join question q on (pq.professor_id=:p_id and pq.exam_id=:e_id and pq.question_id=q.id and q.theme_id=:id)")
                .setParameter("p_id",professor_id).setParameter("e_id",exam_id).setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Professor_question> professor_questions=new ArrayList<>();
        for(SqlRow row : rows){
            professor_questions.add(Professor_question.find.byId(row.getInteger("pq_id")));
        }
        return professor_questions;
    }
}
