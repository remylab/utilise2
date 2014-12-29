package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import models.Member;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render());
    }
    
    public static Result about() {
        return ok(views.html.about.render());
    }
    
    public static Result contact() {
        return ok(views.html.contact.render());
    }
    
    public static Result pictures() {
        return ok(views.html.pictures.render());
    }

    public static void onLogin(String username) {
        session("username", username);
    }
    

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", 
        		controllers.routes.javascript.Admin.addPost(),
        		controllers.routes.javascript.Admin.updatePost()
        		));
    }
}
