package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Test extends Model{
    @Id
    public int id;
    public int timeLeft;
    public int finish;
    public float score;
    @OneToOne
    @JoinColumn(name="student_id")
    public Student student;
    @ManyToOne
    @JoinColumn(name="exam_id")
    public Exam exam;
    @OneToMany(mappedBy = "test")
    public List<Test_question> test_questions;
    public Test(int timeLeft,int student_id,int exam_id){
        this.timeLeft=timeLeft;
        student=Student.find.byId(student_id);
        exam=Exam.find.byId(exam_id);
    }
    public static Test create(int timeLeft,int student_id,int exam_id){
        Test test=new Test(timeLeft,student_id,exam_id);
        test.save();
        return test;
    }
    public void calculateScore(){
        float count=0;
        for(Test_question test_question:test_questions){
            count+=test_question.getScore();
        }
        score=count;
        update();
    }
    public static Finder<Integer,Test> find = new Finder<>(Integer.class,Test.class);
}
