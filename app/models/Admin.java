package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Admin extends Model{
    @Id
    public int id;
    public String name;
    public String lastname;
    @OneToOne
    @JoinColumn(name="user_id")
    public User user;
    public Admin(String name,String lastname,int user_id){
        this.name=name;
        this.lastname=lastname;
        user=User.find.byId(user_id);
    }
    public static Admin create(String name,String lastname,int user_id){
        Admin admin=new Admin(name,lastname,user_id);
        admin.save();
        return admin;
    }
    public static Finder<Integer,Professor> find = new Finder<>(Integer.class,Professor.class);
}
