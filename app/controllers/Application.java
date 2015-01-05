package controllers;

import play.Routes;
import play.mvc.Controller;
import play.mvc.Result;
import tools.StringUtil;
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
    	
    	Long postId = StringUtil.getBlogIdFromTitle(title);
    	
    	if ( postId != null ) {

        	BlogPost post = BlogPost.findBlogById(postId); 
        	
        	if ( post != null && post.isOnline) {
        		return ok(views.html.blog.fullpost.render(post));
        	} 
    	}
		return ok(views.html.notfound.render());
    }
    
    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", 
        		controllers.routes.javascript.Admin.addPost(),
        		controllers.routes.javascript.Admin.updatePost()
        		));
    }
}
