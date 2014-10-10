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
        Admin admin= User.findByEmail(session("email")).admin;
        return ok(index.render(admin));
    }
    public static Result listTheme(){
        DynamicForm data = form().bindFromRequest();
        int courseid=Integer.parseInt(data.get("courseid"));
        List<Map<String, String>> themes=new ArrayList<>();
        for(Theme theme:Course.find.byId(courseid).themes){
            Map<String,String> map=new HashMap<>();
            map.put("id",""+theme.id);
            map.put("name",theme.name);
            themes.add(map);
        }
        return ok(Json.toJson(themes));
    }
    public static Result validateCourses(){
        DynamicForm data = form().bindFromRequest();
        String delimiter=data.get("delimiter");
        String error="",msg="";
        String names[]=data.get("names").split(delimiter);
        for(String name:names){
            if(Course.find.where().eq("name",name).findList().size()>0){
                msg+=","+name;
            }
        }
        if(!msg.equals("")){
            error="Los cursos: "+msg.substring(1)+" ya existen";
        }
        return ok(error);
    }
    public static Result validateThemes(){
        DynamicForm data = form().bindFromRequest();
        String delimiter=data.get("delimiter");
        String error="",msg="";
        String names[]=data.get("names").split(delimiter);
        int course_id=Integer.parseInt(data.get("course_id"));
        for(String name:names){
            if(Theme.find.where().eq("name",name).eq("course_id",course_id).findList().size()>0){
                msg+=","+name;
            }
        }
        if(!msg.equals("")){
            error="Los temas: "+msg.substring(1)+" del curso "+Course.find.byId(course_id).name+" ya existen";
        }
        return ok(error);
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
