package controllers;

import models.Member;
import play.mvc.*;

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
    
    public static Result admin() {
        Member member = Membership.getUser();
        return ok(views.html.admin.index.render(member));
    }


    public static void onLogin(String username) {
        session("username", username);
    }
}
