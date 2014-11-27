package models;

import com.avaje.ebean.*;
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
        map.put("startDate",startDate!=null?Application.getDate(startDate):"");
        map.put("lastDate",lastDate!=null?Application.getDate(lastDate):"");
        return map;
    }
    public void update(String name,String duration,String startDate,String lastDate){
        this.name=name;
        this.duration=duration;
        if(Application.getDate(startDate)!=null)this.startDate=Application.getDate(startDate);
        if(Application.getDate(lastDate)!=null)this.lastDate=Application.getDate(lastDate);
        update();
    }
    public void addCourse(int course_id){
        Course course=Course.find.byId(course_id);
        addCourse(course);
    }
    public void addCourse(Course course){
        courses.add(course);
        update();
    }
    public void removeCourse(int course_id){
        Course course=Course.find.byId(course_id);
        removeCourse(course);
    }
    public void removeCourse(Course course){
        courses.remove(course);
        update();
    }
    public static Model.Finder<Integer,Cycle> find = new Finder<>(Integer.class,Cycle.class);
    public List<Asignature> asignatures(){
        SqlQuery query = Ebean.createSqlQuery("select a.professor_id pid,a.course_id cid,a.section_id sid from cycle cy join section s on (cy.id=:id and cy.id=s.cycle_id)" +
                "join asignature a on (s.id=a.section_id)")
                .setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Asignature> asignatures=new ArrayList<>();
        for(SqlRow row : rows){
            asignatures.add(Asignature.findById(row.getInteger("pid"),row.getInteger("cid"),row.getInteger("sid")));
        }
        return asignatures;
    }
    public List<Professor_question> professor_questions(int professor_id){
        SqlQuery query = Ebean.createSqlQuery("select pq.id pq_id from professor_question pq join exam e on (pq.professor_id=:p_id and pq.exam_id=e.id and e.cycle_id=:id)")
                .setParameter("p_id",professor_id).setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Professor_question> professor_questions=new ArrayList<>();
        for(SqlRow row : rows){
            professor_questions.add(Professor_question.find.byId(row.getInteger("pq_id")));
        }
        return professor_questions;
    }
}
