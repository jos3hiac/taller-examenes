package models;

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
        return map;
    }
    public void updateChoice(int choice_id){
        choice=Choice.find.byId(choice_id);
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
        return getAbsolutePath()+"\\"+id;
    }
    public static String getAbsolutePath(){
        return Play.application().path().getAbsolutePath()+"\\public\\preguntas";
    }
}
