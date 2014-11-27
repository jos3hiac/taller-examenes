package models;

import com.avaje.ebean.*;
import controllers.Application;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Exam extends Model{
    @Id
    public int id;
    //@Formats.DateTime(pattern="dd/MM/yyyy")
    public Date date = new Date();
    @ManyToOne
    @JoinColumn(name="cycle_id")
    public Cycle cycle;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="exam_x_theme", joinColumns={@JoinColumn(name="exam_id")}, inverseJoinColumns={@JoinColumn(name="theme_id")})
    public List<Theme> themes;
    @OneToMany(mappedBy = "exam")
    public List<Professor_question> professor_questions;

    public Exam(Date date,int cycle_id){
        this.date=date;
        cycle=Cycle.find.byId(cycle_id);
    }
    public static Exam create(Date date,int cycle_id){
        Exam exam=new Exam(date,cycle_id);
        exam.save();
        return exam;
    }
    public void update(String date){
        if(Application.getDate(date)!=null)this.date=Application.getDate(date);
        update();
    }
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        map.put("date",Application.getDateTime(date));
        return map;
    }
    public void addTheme(int theme_id){
        Theme theme=Theme.find.byId(theme_id);
        addTheme(theme);
    }
    public void addTheme(Theme theme){
        themes.add(theme);
        update();
    }
    public void removeTheme(int theme_id){
        Theme theme=Theme.find.byId(theme_id);
        removeTheme(theme);
    }
    public void removeTheme(Theme theme){
        themes.remove(theme);
        update();
    }
    public static Finder<Integer,Exam> find = new Finder<>(Integer.class,Exam.class);

    public List<Professor_question> professor_questions(int professor_id){
        SqlQuery query = Ebean.createSqlQuery("select pq.id pq_id from professor_question pq where(pq.professor_id=:p_id and pq.exam_id=:id)")
                .setParameter("p_id",professor_id).setParameter("id",id);
        List<SqlRow> rows = query.findList();
        List<Professor_question> professor_questions=new ArrayList<>();
        for(SqlRow row : rows){
            professor_questions.add(Professor_question.find.byId(row.getInteger("pq_id")));
        }
        return professor_questions;
    }
}
