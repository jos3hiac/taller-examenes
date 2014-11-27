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
    public Map getMap(){
        Map<String,String> map=new HashMap<>();
        map.put("id",id+"");
        if(value!=null)map.put("value",value);
        if(image!=null)map.put("image",image);
        if(image!=null)map.put("imagepath",getImagePath());
        return map;
    }
    public void update(String value,String image){
        this.value=value;
        this.image=image;
        update();
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
