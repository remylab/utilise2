package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import models.BlogPost;
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
    
    public static Result journal(int page) {
        return ok(views.html.journal.render(page));
    }
    
    public static Result fullpost(String title) {
    	int index = title.lastIndexOf("-");
    	
    	if ( index > 0) {
        	String postId = title.substring(index+1);
        	Long id = Long.parseLong(postId, 10);   
        	BlogPost post = BlogPost.finder.byId(id);
    		return ok(views.html.blog.fullpost.render(post));
    	}
    	return ok(views.html.journal.render(1));
    }
    
    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", 
        		controllers.routes.javascript.Admin.addPost(),
        		controllers.routes.javascript.Admin.updatePost()
        		));
    }
}
