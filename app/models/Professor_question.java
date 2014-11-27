package models;

import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Professor_question extends Model{
    @Id
    public int id;
    @ManyToOne
    @JoinColumn(name="professor_id")
    public Professor professor;
    @ManyToOne
    @JoinColumn(name="exam_id")
    public Exam exam;
    @ManyToOne
    @JoinColumn(name="question_id")
    public Question question;
    @OneToOne(mappedBy = "professor_question")
    public Exam_question exam_question;
    public Professor_question(int professor_id, int examen_id, int question_id){
        professor=Professor.find.byId(professor_id);
        exam=Exam.find.byId(examen_id);
        question=Question.find.byId(question_id);
    }
    public static Professor_question create(int professor_id,int examen_id,int question_id){
        Professor_question professor_question =new Professor_question(professor_id,examen_id,question_id);
        professor_question.save();
        return professor_question;
    }
    public static Finder<Integer, Professor_question> find = new Finder<>(Integer.class, Professor_question.class);
}
