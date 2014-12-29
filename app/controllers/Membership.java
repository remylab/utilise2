package controllers;

import com.avaje.ebean.Ebean;

import models.Member;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Membership extends Controller {
    public final static Form<LoginModel> loginForm = Form.form(LoginModel.class);

    public static class LoginModel {
        public String username;
        public String password;
    }

    public static void onLogin(String username) {
        session("username", username);
    }
    
    public static void onLogout() {
        session().remove("username");
    }
    
    public static Result logout() {
        onLogout();
        return redirect(routes.Admin.index(1));
    }
    public static Result login() {
        Form<LoginModel> form = loginForm.bindFromRequest();

        if (Member.authenticate(form.get().username, form.get().password) == null) {
            flash("errorLogin", "");
        } else {
            onLogin(form.get().username);
        }
        return redirect(routes.Admin.index(1));

    }

    public static Member getUser() {
        return (Ebean.find(Member.class).where().eq("username", session("username")).findUnique());
    }
}
