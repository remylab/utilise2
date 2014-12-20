package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {

        return ok(index.render());
    }
    
    public static Result about() {

        return ok(about.render());
    }
    
    public static Result contact() {

        return ok(contact.render());
    }
    
    public static Result pictures() {

        return ok(pictures.render());
    }

}
