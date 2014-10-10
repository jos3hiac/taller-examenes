package controllers;

import models.*;
import play.mvc.*;
import views.html.alumno.index;

public class Alumno extends Controller {
    public static Result index() {
        if(NoAuth()!=null)return NoAuth();
        Student student= User.findByEmail(session("email")).student;
        return ok(index.render(student));
    }
    private static Result NoAuth(){
        if(session("email")==null)
            return redirect(routes.Application.login());
        else
            return null;
    }
}
