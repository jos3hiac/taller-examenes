package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class User extends Model {
    @Id
    public Integer id;
    public String email;
    public String pass;
    @ManyToOne
    @JoinColumn(name="role_id")
    public Role role;
    @OneToOne(mappedBy = "user")
    public Professor professor;
    @OneToOne(mappedBy = "user")
    public Student student;
    @OneToOne(mappedBy = "user")
    public Admin admin;

    public User(String email,String pass,int role_id){
        this.email=email;
        this.pass=pass;
        role=Role.find.byId(role_id);
    }
    public static User create(String email,String pass,int role_id){
        User user=new User(email,pass,role_id);
        user.save();
        return user;
    }
    public static Finder<Integer,User> find = new Finder<>(Integer.class,User.class);

    public static User findByEmail(String email){
        return find.where().eq("email",email).findUnique();
    }
    public static User authenticate(String email,String pass){
        return find.where().eq("email", email).eq("pass", pass).findUnique();
    }
}
