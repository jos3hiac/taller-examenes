package controllers;

import models.*;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.profesor.index;
import views.html.profesor.list;
import views.html.profesor.listquestion;
import java.io.File;

import static play.data.Form.form;

public class Profesor extends Controller {
    public static Result index(){
        if(NoAuth()!=null)return NoAuth();
        return ok(index.render(Application.user().professor));
    }
    public static Result list(){
        DynamicForm data = form().bindFromRequest();
        String[] ids=data.get("id").split("\\.");
        if(ids.length==0)ids=new String[]{data.get("id")};
        int length=ids.length;
        String model=length==1?"cycle":length==2?"exam":length==3?"course":"none";
        Cycle cycle=Cycle.find.byId(Integer.parseInt(ids[0]));
        Exam exam=Exam.find.byId(length>1?Integer.parseInt(ids[1]):0);
        Course course=Course.find.byId(length>2?Integer.parseInt(ids[2]):0);
        return ok(list.render(model,course,exam,cycle,Application.user().professor));
    }
    public static Result listQuestion(){
        DynamicForm data = form().bindFromRequest();
        int order=Integer.parseInt(data.get("order"));
        int id=Integer.parseInt(data.get("id"));
        String model=order==3?"course":order==4?"theme":"other";
        Course course=Course.find.byId(order==3?id:0);
        Theme theme=Theme.find.byId(order==4?id:0);
        return ok(listquestion.render(model,theme,course));
    }
    public static Result saveProfessorQuestion(){
        DynamicForm data = form().bindFromRequest();
        int professorid=Application.user().professor.id;
        int examenid=Integer.parseInt(data.get("examenid"));
        int questionid=Integer.parseInt(data.get("questionid"));
        Professor_question professor_question=Professor_question.create(professorid,examenid,questionid);
        return ok(professor_question.id+"");
    }
    public static Result saveQuestion(){
        DynamicForm data = form().bindFromRequest();
        String base=data.get("dataurl").split(",")[1];
        int themeid=Integer.parseInt(data.get("themeid"));
        Question question=Question.create(null,data.get("name")+","+data.get("rect"),themeid);
        String path=question.getPath();
        new File(path).mkdirs();
        Application.B64ToFile(base,path,data.get("name"));
        return ok(question.id+"");
    }
    public static Result updateChoiceIdQuestion(){
        DynamicForm data = form().bindFromRequest();
        Question question=Question.find.byId(Integer.parseInt(data.get("questionid")));
        question.updateChoice(Integer.parseInt(data.get("choiceid")));
        return ok("success");
    }
    public static Result saveChoice(){
        DynamicForm data = form().bindFromRequest();
        String base=data.get("dataurl").split(",")[1];
        int questionid=Integer.parseInt(data.get("questionid"));
        Choice choice=Choice.create(null,data.get("name")+","+data.get("rect"),questionid);
        String path=Question.getAbsolutePath(questionid);
        Application.B64ToFile(base,path,data.get("name"));
        return ok(choice.id+"");
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
