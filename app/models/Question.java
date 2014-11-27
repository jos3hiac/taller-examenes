package models;

import com.avaje.ebean.*;
import play.Play;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Question extends Model{
    @Id
    public Integer id;
    public String value;
    public String image;
    @OneToOne
    @JoinColumn(name="choice_id")
    public Choice choice;
    @ManyToOne
    @JoinColumn(name="theme_id")
    public Theme theme;
    @OneToMany(mappedBy = "question")
    public List<Choice> choices;
    @OneToMany(mappedBy = "question")
    public List<Professor_question> professor_questions;

    public Question(String value,String image,int theme_id){
        this.value=value;
        this.image=image;
        theme=Theme.find.byId(theme_id);
    }
    public static Question create(String value,String image,int theme_id){
        Question question=new Question(value,image,theme_id);
        question.save();
        return question;
    }
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        if(value!=null)map.put("value",value);
        if(image!=null)map.put("image",image);
        if(image!=null)map.put("imagepath",getImagePath());
        if(choice!=null)map.put("choice_id",choice.id+"");
        return map;
    }
    public void updateChoice(int choice_id){
        choice=Choice.find.byId(choice_id);
        update();
    }
    public void update(String value,String image){
        this.value=value;
        this.image=image;
        update();
    }
    public static Model.Finder<Integer,Question> find = new Model.Finder<>(Integer.class,Question.class);

    public String getTokenImage(int pos){
        String[] tokens=image.split(",");
        String token="";
        if(pos>0 && pos<=tokens.length)token=tokens[pos-1];
        return token;
    }
    public String getImagePath(){return "assets/preguntas/"+id+"/"+getTokenImage(1);}
    public String getPath(){
        return getAbsolutePath(id);
    }
    public static String getAbsolutePath(int id){
        return getAbsolutePath()+"/"+id;
    }
    public static String getAbsolutePath(){
        return Play.application().path().getAbsolutePath().replace("\\","/")+"/public/preguntas";
    }
    public static String AssetToPath(String asset){
        return Play.application().path().getAbsolutePath().replace("\\","/")+"/public"+asset.split("assets")[1];
    }
    public static List<Question> notBeProfessor(int professor_id,int theme_id){
        SqlQuery query = Ebean.createSqlQuery("select pq.question_id q_id from professor_question pq "+
                "join question q on (pq.professor_id <> :p_id and pq.question_id=q.id and q.theme_id=:t_id) group by(pq.question_id)")
                .setParameter("p_id",professor_id).setParameter("t_id",theme_id);
        List<SqlRow> rows = query.findList();
        List<Question> questions=new ArrayList<>();
        for(SqlRow row : rows){
            questions.add(Question.find.byId(row.getInteger("q_id")));
        }
        return questions;
    }
    public static List<SqlRow> notBeExam(int exam_id,int theme_id){
        SqlQuery query = Ebean.createSqlQuery("select q.id q_id,pq.exam_id e_id from professor_question pq"+
                " right join question q on (pq.question_id=q.id) where(q.theme_id=:t_id and (pq.exam_id IS NULL or pq.exam_id <> :e_id))")
                .setParameter("e_id",exam_id).setParameter("t_id",theme_id);
        List<SqlRow> rows = query.findList();
        return rows;
    }
}
