package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Choice extends Model{
    @Id
    public int id;
    public String value;
    public String image;
    @ManyToOne
    @JoinColumn(name="question_id")
    public Question question;
    @OneToOne(mappedBy = "choice")
    public Question questionr;

    public Choice(String value,String image,int question_id){
        this.value=value;
        this.image=image;
        question=Question.find.byId(question_id);
    }
    public static Choice create(String value,String image,int question_id){
        Choice choice=new Choice(value,image,question_id);
        choice.save();
        return choice;
    }
    public static Finder<Integer,Choice> find = new Finder<>(Integer.class,Choice.class);

    public String getTokenImage(int pos){
        String[] tokens=image.split(",");
        String token="";
        if(pos>0 && pos<=tokens.length)token=tokens[pos-1];
        return token;
    }
    public String getImagePath(){return "assets/preguntas/"+question.id+"/"+getTokenImage(1);}
}
