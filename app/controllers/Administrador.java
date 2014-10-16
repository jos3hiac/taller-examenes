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
    public static Result findCourse(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        return ok(Json.toJson(Course.find.byId(id).getMap()));
    }
    public static Result findTheme(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        return ok(Json.toJson(Theme.find.byId(id).getMap()));
    }
    public static Result listTheme(){
        DynamicForm data = form().bindFromRequest();
        int course_id=Integer.parseInt(data.get("course_id"));
        List<Map<String, String>> themes=new ArrayList<>();
        for(Theme theme:Course.find.byId(course_id).themes){
            Map<String,String> map=new HashMap<>();
            map.put("id",""+theme.id);
            map.put("name",theme.name);
            themes.add(map);
        }
        return ok(Json.toJson(themes));
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
    public static Result updateCourse(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        Course.find.byId(id).updateName(data.get("name"));
        return ok();
    }
    public static Result updateTheme(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        Theme.find.byId(id).updateName(data.get("name"));
        return ok();
    }
    public static Result deleteCourse(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        Course.find.byId(id).delete();
        return ok();
    }
    public static Result deleteTheme(){
        DynamicForm data = form().bindFromRequest();
        int id=Integer.parseInt(data.get("id"));
        Theme.find.byId(id).delete();
        return ok();
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
