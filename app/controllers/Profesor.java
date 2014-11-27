package controllers;

import com.avaje.ebean.SqlRow;
import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;
import views.html.profesor.index;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;

public class Profesor extends Controller {
    public static Result index(){
        if(NoAuth()!=null)return NoAuth();
        return ok(index.render(Application.user().professor));
    }
    public static Result listQuestion(){
        DynamicForm data = form().bindFromRequest();
        int order=Integer.parseInt(data.get("order"));
        int id=Integer.parseInt(data.get("id"));
        List<Map<String, String>> questions=new ArrayList<>();
        if(order==3){
            for(Question question : Course.find.byId(id).questions()){
                questions.add(question.getMap());
            }
        }
        else if(order==4){
            for(Question question : Theme.find.byId(id).questions){
                questions.add(question.getMap());
            }
        }
        else{
            for(Question question : Question.find.all()){
                questions.add(question.getMap());
            }
        }
        return ok(Json.toJson(questions));
    }
    public static Result listRelatedQuestion(){
        DynamicForm data = form().bindFromRequest();
        int theme_id=Integer.parseInt(data.get("theme_id"));
        int exam_id=Integer.parseInt(data.get("exam_id"));
        List<Map<String, String>> questions=new ArrayList<>();
        for(SqlRow row : Question.notBeExam(exam_id, theme_id)){
            Map<String, String> question=Question.find.byId(row.getInteger("q_id")).getMap();
            if(row.getInteger("e_id")!=null){
                question.put("exam_date",Application.getDateTime(Exam.find.byId(row.getInteger("e_id")).date));
                question.put("cycle_name",Exam.find.byId(row.getInteger("e_id")).cycle.name);
            }
            questions.add(question);
        }
        return ok(Json.toJson(questions));
    }
    public static Result listProfessorQuestion(){
        DynamicForm data = form().bindFromRequest();
        int order=Integer.parseInt(data.get("order"));
        int id=Integer.parseInt(data.get("id"));
        int professor_id=Application.user().professor.id;
        int exam_id=0;
        if(data.get("exam_id")!=null)exam_id=Integer.parseInt(data.get("exam_id"));
        List<Map<String, String>> questions=new ArrayList<>();
        if(order==1){
            for(Professor_question professor_question: Cycle.find.byId(id).professor_questions(professor_id)){
                Map<String, String> question=professor_question.question.getMap();
                question.put("pq_id",professor_question.id+"");
                question.put("exam_date",Application.getDateTime(professor_question.exam.date));
                question.put("course_name",professor_question.question.theme.course.name);
                question.put("theme_name",professor_question.question.theme.name);
                questions.add(question);
            }
        }
        if(order==2){
            for(Professor_question professor_question: Exam.find.byId(id).professor_questions(professor_id)){
                Map<String, String> question=professor_question.question.getMap();
                question.put("pq_id",professor_question.id+"");
                question.put("exam_date",Application.getDateTime(professor_question.exam.date));
                question.put("course_name",professor_question.question.theme.course.name);
                question.put("theme_name",professor_question.question.theme.name);
                questions.add(question);
            }
        }
        if(order==3){
            for(Professor_question professor_question: Course.find.byId(id).professor_questions(professor_id,exam_id)){
                Map<String, String> question=professor_question.question.getMap();
                question.put("pq_id",professor_question.id+"");
                question.put("exam_date",Application.getDateTime(professor_question.exam.date));
                question.put("course_name",professor_question.question.theme.course.name);
                question.put("theme_name",professor_question.question.theme.name);
                questions.add(question);
            }
        }
        if(order==4){
            for(Professor_question professor_question: Theme.find.byId(id).professor_questions(professor_id,exam_id)){
                Map<String, String> question=professor_question.question.getMap();
                question.put("pq_id",professor_question.id+"");
                question.put("exam_date",Application.getDateTime(professor_question.exam.date));
                question.put("course_name",professor_question.question.theme.course.name);
                question.put("theme_name",professor_question.question.theme.name);
                questions.add(question);
            }
        }
        return ok(Json.toJson(questions));
    }
    public static Result listChoice(){
        DynamicForm data = form().bindFromRequest();
        int question_id=Integer.parseInt(data.get("question_id"));
        List<Map<String, String>> choices=new ArrayList<>();
        for (Choice choice : Question.find.byId(question_id).choices){
            choices.add(choice.getMap());
        }
        return ok(Json.toJson(choices));
    }
    public static Result listCourse(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        List<Map<String, String>> courses=new ArrayList<>();
        for (Course course : Professor.find.byId(Application.user().professor.id).courses(cycle_id)){
            courses.add(course.getMap());
        }
        return ok(Json.toJson(courses));
    }
    public static Result saveProfessorQuestion(){
        DynamicForm data = form().bindFromRequest();
        int professor_id=Application.user().professor.id;
        int examen_id=Integer.parseInt(data.get("examen_id"));
        int question_id=Integer.parseInt(data.get("question_id"));
        Professor_question professor_question = Professor_question.create(professor_id, examen_id, question_id);
        return ok(professor_question.id+"");
    }
    public static Result saveQuestion(){
        DynamicForm data = form().bindFromRequest();
        Question question;
        int theme_id=Integer.parseInt(data.get("theme_id"));
        if(data.get("value")!=null){
            question=Question.create(data.get("value"),null,theme_id);
        }
        else{
            question=Question.create(null,data.get("name")+","+data.get("rect"),theme_id);
            String path=question.getPath();
            if(!new File(path).exists())new File(path).mkdirs();
            if(data.get("dataurl").split(",").length==2){
                String base=data.get("dataurl").split(",")[1];
                Application.B64ToFile(base,path,data.get("name"));
            }
            else{
                Application.CopyFile(new File(Question.AssetToPath(data.get("dataurl"))),new File(path+"/"+data.get("name")));
            }
        }
        return ok(question.id+"");
    }
    public static Result updateQuestion(){
        DynamicForm data = form().bindFromRequest();
        Question question=Question.find.byId(Integer.parseInt(data.get("question_id")));
        if(question.image!=null){
            File file=new File(question.getPath()+"/"+question.getTokenImage(1));
            if(file.exists())file.delete();
        }
        if(data.get("value")!=null){
            question.update(data.get("value"),null);
        }
        else{
            question.update(null,data.get("name")+","+data.get("rect"));
            if(data.get("dataurl").split(",").length==2){
                String base=data.get("dataurl").split(",")[1];
                Application.B64ToFile(base,question.getPath(),data.get("name"));
            }
            else{
                Application.CopyFile(new File(Question.AssetToPath(data.get("dataurl"))),new File(question.getPath()+"/"+data.get("name")));
            }
        }
        return ok();
    }
    public static Result updateChoiceIdQuestion(){
        DynamicForm data = form().bindFromRequest();
        Question question=Question.find.byId(Integer.parseInt(data.get("question_id")));
        question.updateChoice(Integer.parseInt(data.get("choice_id")));
        return ok();
    }
    public static Result saveChoice(){
        DynamicForm data = form().bindFromRequest();
        Choice choice;
        int question_id=Integer.parseInt(data.get("question_id"));
        if(data.get("value")!=null){
            choice=Choice.create(data.get("value"),null,question_id);
        }
        else{
            choice=Choice.create(null,data.get("name")+","+data.get("rect"),question_id);
            String path=Question.getAbsolutePath(question_id);
            if(data.get("dataurl").split(",").length==2){
                String base=data.get("dataurl").split(",")[1];
                Application.B64ToFile(base,path,data.get("name"));
            }
            else{
                Application.CopyFile(new File(Question.AssetToPath(data.get("dataurl"))),new File(path+"/"+data.get("name")));
            }
        }
        return ok(choice.id+"");
    }
    public static Result updateChoice(){
        DynamicForm data = form().bindFromRequest();
        Choice choice=Choice.find.byId(Integer.parseInt(data.get("choice_id")));
        if(choice.image!=null){
            File file=new File(Question.getAbsolutePath(choice.question.id)+"/"+choice.getTokenImage(1));
            if(file.exists())file.delete();
        }
        if(data.get("value")!=null){
            choice.update(data.get("value"),null);
        }
        else{
            choice.update(null,data.get("name")+","+data.get("rect"));
            if(data.get("dataurl").split(",").length==2){
                String base=data.get("dataurl").split(",")[1];
                Application.B64ToFile(base,Question.getAbsolutePath(choice.question.id),data.get("name"));
            }
            else{
                Application.CopyFile(new File(Question.AssetToPath(data.get("dataurl"))),new File(Question.getAbsolutePath(choice.question.id)+"/"+data.get("name")));
            }
        }
        return ok();
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
