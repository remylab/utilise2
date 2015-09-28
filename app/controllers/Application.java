package controllers;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.Routes;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import tools.StringUtil;
import models.BlogPost;
import models.Member;
import models.Subscriber;
import tools.Util;

public class Application extends Controller {

    public static Result index() {
        return ok(views.html.index.render());
    }
    
    public static Result about() {
        return ok(views.html.about.render());
    }
    
    public static Result coaching() {
        return ok(views.html.coaching.render());
    }
    
    public static Result contact() {
        return ok(views.html.contact.render());
    }
    
    public static Result reading() {
        return ok(views.html.reading.render());
    }
    
    public static Result ateliers() {
        return ok(views.html.ateliers.render());
    }
    
    public static Result tarot() {
        return ok(views.html.tarot.render());
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
    
    public static Result sendMessage(String message) {
    	
        if ( !StringUtils.isEmpty(message) ) {
    		try {

    	    	String body = "<p>"+message+"</p>";
    	    	tools.Util.sendEmail("Nouveau message","no-reply@utilisetoncorps.ca","utilisetoncorps@gmail.com",body);
    	
    		} catch (Exception e) {
    			// ignore
    		}
        }

        return ok();
    }
    
    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", 
        		controllers.routes.javascript.Admin.addPost(),
        		controllers.routes.javascript.Admin.updatePost(),
        		controllers.routes.javascript.Newsletter.addSubscriber(),
        		controllers.routes.javascript.Newsletter.sendPost(),
        		controllers.routes.javascript.Newsletter.sendNewsletter(),
        		controllers.routes.javascript.Newsletter.unsubscribeConfirm(),
        		controllers.routes.javascript.Application.sendMessage()
        		));
    }
}
