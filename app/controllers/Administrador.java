package controllers;

import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;
import views.html.admin.index;

import java.util.*;

import static play.data.Form.form;

public class Administrador extends Controller {
    public static Result index() {
        if(NoAuth()!=null)return NoAuth();
        return ok(index.render(Application.user().admin));
    }
    public static Result findModel(){
        DynamicForm data = form().bindFromRequest();
        String model=data.get("model");
        int id=Integer.parseInt(data.get("id"));
        Map<String,String> map=null;
        switch(model) {
            case "course":map=Course.find.byId(id).getMap();break;
            case "theme":map=Theme.find.byId(id).getMap();break;
            case "cycle":map=Cycle.find.byId(id).getMap();break;
            case "section":map=Section.find.byId(id).getMap();break;
            case "professor":map=Professor.find.byId(id).getMap();break;
            case "student":map=Student.find.byId(id).getMap();break;
        }
        return ok(Json.toJson(map));
    }
    public static Result listTheme(){
        DynamicForm data = form().bindFromRequest();
        int course_id=Integer.parseInt(data.get("course_id"));
        List<Map<String, String>> themes=new ArrayList<>();
        for(Theme theme:Course.find.byId(course_id).themes){
            themes.add(theme.getMap());
        }
        return ok(Json.toJson(themes));
    }
    public static Result listCycle(){
        DynamicForm data = form().bindFromRequest();
        int academy_id=Integer.parseInt(data.get("academy_id"));
        List<Map<String, String>> cycles=new ArrayList<>();
        for(Cycle cycle:Academy.find.byId(academy_id).cycles){
            cycles.add(cycle.getMap());
        }
        return ok(Json.toJson(cycles));
    }
    public static Result listSection(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        List<Map<String, String>> sections=new ArrayList<>();
        for(Section section:Cycle.find.byId(cycle_id).sections){
            sections.add(section.getMap());
        }
        return ok(Json.toJson(sections));
    }
    public static Result listCycleCourses(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        List<Map<String, String>> courses=new ArrayList<>();
        for (Course course : Cycle.find.byId(cycle_id).courses){
            courses.add(course.getMap());
        }
        return ok(Json.toJson(courses));
    }
    public static Result listCoursesNotInCycle(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        List<Map<String, String>> courses=new ArrayList<>();
        for (Course course : Course.NotInCycle(cycle_id)){
            courses.add(course.getMap());
        }
        return ok(Json.toJson(courses));
    }
    public static Result addCourses(){
        DynamicForm data = form().bindFromRequest();
        List<Map<String, String>> courses=new ArrayList<>();
        for (String name : data.get("names").split(data.get("delimiter"))) {
            Course course = Course.create(name);
            courses.add(course.getMap());
        }
        return ok(Json.toJson(courses));
    }
    public static Result addThemes(){
        DynamicForm data = form().bindFromRequest();
        int course_id=Integer.parseInt(data.get("course_id"));
        List<Map<String, String>> themes=new ArrayList<>();
        for(String name:data.get("names").split(data.get("delimiter"))){
            Theme theme=Theme.create(name,course_id);
            themes.add(theme.getMap());
        }
        return ok(Json.toJson(themes));
    }
    public static Result addCycles(){
        DynamicForm data = form().bindFromRequest();
        int academy_id=Integer.parseInt(data.get("academy_id"));
        List<Map<String, String>> cycles=new ArrayList<>();
        for(String name:data.get("names").split(data.get("delimiter"))){
            Cycle cycle=Cycle.create(name,academy_id);
            cycles.add(cycle.getMap());
        }
        return ok(Json.toJson(cycles));
    }
    public static Result addSections(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        List<Map<String, String>> sections=new ArrayList<>();
        for(String name:data.get("names").split(data.get("delimiter"))){
            Section section=Section.create(name,cycle_id);
            sections.add(section.getMap());
        }
        return ok(Json.toJson(sections));
    }
    public static Result addCycleCourse(){
        DynamicForm data = form().bindFromRequest();
        int cycle_id=Integer.parseInt(data.get("cycle_id"));
        int course_id=Integer.parseInt(data.get("course_id"));
        Course course=Course.find.byId(course_id);
        Cycle.find.byId(cycle_id).addCourse(course);
        return ok(Json.toJson(course.getMap()));
    }
    public static Result addProfessor(){
        DynamicForm data = form().bindFromRequest();
        int user_id=Integer.parseInt(data.get("user_id"));
        Professor professor=Professor.create(data.get("name"),data.get("lastname"),user_id);
        return ok(Json.toJson(professor.getMap()));
    }
    public static Result addStudent(){
        DynamicForm data = form().bindFromRequest();
        int user_id=Integer.parseInt(data.get("user_id"));
        Student student=Student.create(data.get("name"),data.get("lastname"),user_id);
        return ok(Json.toJson(student.getMap()));
    }
    public static Result addUser(){
        DynamicForm data = form().bindFromRequest();
        int role_id=Integer.parseInt(data.get("role_id"));
        User user=User.create(data.get("email"),data.get("pass"),role_id);
        return ok(Json.toJson(user.getMap()));
    }
    public static Result updateModel(){
        DynamicForm data = form().bindFromRequest();
        String model=data.get("model");
        int id=Integer.parseInt(data.get("id"));
        switch(model) {
            case "course":Course.find.byId(id).update(data.get("name"));break;
            case "theme":Theme.find.byId(id).update(data.get("name"));break;
            case "cycle":Cycle.find.byId(id).update(data.get("name"),data.get("duration"),data.get("startDate"),data.get("lastDate"));break;
            case "section":Section.find.byId(id).update(data.get("name"));break;
            case "professor":Professor.find.byId(id).update(data.get("name"),data.get("lastname"));break;
            case "student":Student.find.byId(id).update(data.get("name"),data.get("lastname"));break;
        }
        return ok();
    }
    public static Result deleteModel(){
        DynamicForm data = form().bindFromRequest();
        String model=data.get("model");
        int id=Integer.parseInt(data.get("id"));
        switch(model) {
            case "course":Course.find.byId(id).delete();break;
            case "theme":Theme.find.byId(id).delete();break;
            case "cycle":Cycle.find.byId(id).delete();break;
            case "section":Section.find.byId(id).delete();break;
            case "professor":Professor.find.byId(id).user.delete();Professor.find.byId(id).delete();break;
            case "student":Student.find.byId(id).user.delete();Student.find.byId(id).delete();break;
            case "cyclexcourse":Cycle.find.byId(Integer.parseInt(data.get("cycle_id"))).removeCourse(id);
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
