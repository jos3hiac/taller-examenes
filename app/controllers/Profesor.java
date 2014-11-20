package controllers;

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
        Professor_question professor_question=Professor_question.create(professor_id,examen_id,question_id);
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
            String base=data.get("dataurl").split(",")[1];
            question=Question.create(null,data.get("name")+","+data.get("rect"),theme_id);
            String path=question.getPath();
            new File(path).mkdirs();
            Application.B64ToFile(base,path,data.get("name"));
        }
        return ok(question.id+"");
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
            String base=data.get("dataurl").split(",")[1];
            choice=Choice.create(null,data.get("name")+","+data.get("rect"),question_id);
            String path=Question.getAbsolutePath(question_id);
            Application.B64ToFile(base,path,data.get("name"));
        }
        return ok(choice.id+"");
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
