package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Exam_question extends Model{
    @Id
    public int id;
    public float score_correct;
    public float score_incorrect;
    @OneToOne
    @JoinColumn(name="professor_question_id")
    public Professor_question professor_question;

    public Exam_question(int score_correct,int score_incorrect,int professor_question_id){
        this.score_correct=score_correct;
        this.score_incorrect=score_incorrect;
        professor_question = Professor_question.find.byId(professor_question_id);
    }
    public static Exam_question create(int score_correct,int score_incorrect,int professor_question_id){
        Exam_question exam_question=new Exam_question(score_correct,score_incorrect,professor_question_id);
        exam_question.save();
        return exam_question;
    }
    public static Finder<Integer,Exam_question> find = new Finder<>(Integer.class,Exam_question.class);
}
