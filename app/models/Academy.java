package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Academy extends Model{
    @Id
    public int id;
    public String name;
    public String direction;
    @OneToMany(mappedBy = "academy")
    public List<Cycle> cycles;
    public Academy(String name,String direction){
        this.name=name;
        this.direction=direction;
    }
    public static Academy create(String name,String direction){
        Academy academy=new Academy(name,direction);
        return academy;
    }
    public static Finder<Integer,Academy> find = new Finder<>(Integer.class,Academy.class);
}
