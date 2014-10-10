package models;

import com.avaje.ebean.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Test_question extends Model{
    @Id
    public int id;
    @OneToOne
    @JoinColumn(name="exam_question_id")
    public Exam_question exam_question;
    @ManyToOne
    @JoinColumn(name="choice_id")
    public Choice choice;
    @ManyToOne
    @JoinColumn(name="test_id")
    public Test test;
    public Test_question(int exam_question_id,int test_id){
        exam_question=Exam_question.find.byId(exam_question_id);
        test=Test.find.byId(test_id);
    }
    public static Test_question create(int exam_question_id,int test_id){
        Test_question test_question=new Test_question(exam_question_id,test_id);
        test_question.save();
        return test_question;
    }
    public void updateChoice(int choice_id){
        choice=Choice.find.byId(choice_id);
        update();
    }
    public float getScore(){
        float score;
        if(choice!=null) {
            if (choice.id == exam_question.professor_question.question.choice.id) {
                score = exam_question.score_correct;
            } else {
                score = exam_question.score_incorrect;
            }
        }
        else{
            score=0;
        }
        return score;
    }
    public static Finder<Integer,Test_question> find = new Finder<>(Integer.class,Test_question.class);
}
