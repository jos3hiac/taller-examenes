package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Role extends Model{
    @Id
    public Integer id;
    public String description;
    @OneToMany(mappedBy = "role")
    public List<User> users;
    public Role(String description){
        this.description=description;
    }
    public static Role create(String description){
        Role role=new Role(description);
        role.save();
        return role;
    }
    public static Finder<Integer,Role> find = new Finder<>(Integer.class,Role.class);
}
