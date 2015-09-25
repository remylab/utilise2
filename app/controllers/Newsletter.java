package controllers;

import java.util.List;

import org.springframework.util.StringUtils;

import com.avaje.ebean.Ebean;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import tools.StringUtil;
import models.BlogPost;
import models.Subscriber;

public class Newsletter extends Controller {
	private static String UNSUB_SEED = "etsiondormaitsouslesarbres";
	private static final DynamicForm form = play.data.Form.form();


    
    public static Result index() {
        return ok(views.html.newsletter.index.render());
    }
    
    public static Result addSubscriber(String email) {
    	if ( !StringUtils.isEmpty(email)) {
        	try {
            	Subscriber.addSubscriber(email);
        	} catch (javax.persistence.PersistenceException e) {
        		//ignore
        	}
    	}
    	return ok();
    }
    
    public static Result unsubscribeConfirm() {
    	DynamicForm dynForm = form.bindFromRequest();
    	
    	String email = dynForm.get("email");
    	String token = dynForm.get("token");
    	
    	String goodToken = getToken(email);
    	
    	if (goodToken.equals(token)) {	
    		Subscriber.removeSubscriber(email);
    	}
    	return ok();
    }
    
    public static Result unsubscribe(String email,String token) {
    	
    	return ok(views.html.admin.unsubscribe.render(email,token));
    }
    
    @Security.Authenticated(Secured.class)
    public static Result previewNewsletter(Long postId) {

    	BlogPost post = BlogPost.findBlogById(postId); 
    	
    	List<Subscriber> subList = Ebean.find(Subscriber.class).findList();

    	String baseUrl = "http://" + request().host() ;
    	String preview = views.html.newsletter.templatePost.render(baseUrl,post, "email@test.com", "token").body();
    	
    	return ok(views.html.admin.previewnewsletter.render(post,preview));
    }


    @Security.Authenticated(AjaxSecured.class)
    public static Result sendPost(Long postId) {

    	BlogPost post = BlogPost.findBlogById(postId); 
    	
    	List<Subscriber> subList = Ebean.find(Subscriber.class).findList();

    	
    	String baseUrl = "http://" + request().host() ;
    	if ( post != null) {
        	for(Subscriber sub : subList) {
        		try {

        	    	String body = views.html.newsletter.templatePost.render(baseUrl,post, sub.email, getToken(sub.email)).body();
        	    	tools.Util.sendEmailText("Nouveau billet : " + post.title,"Utilisetoncorps.ca <no-reply@utilisetoncorps.ca>",sub.email,body);
        	
        		} catch (Exception e) {
        			return Results.internalServerError();
        		}
        	}
        	BlogPost.updateNewsletter(post);
        	
        	
        	return ok();
    	}
    	return badRequest("postid not found : " + postId);
    }


    @Security.Authenticated(AjaxSecured.class)
    public static Result sendNewsletter() {
    	DynamicForm dynForm = form.bindFromRequest();
    	
    	String emailSubject = dynForm.get("title");
    	String emailBody = dynForm.get("body");
    	
    	
    	List<Subscriber> subList = Ebean.find(Subscriber.class).findList();

    	
    	String baseUrl = "http://" + request().host() ;
    	for(Subscriber sub : subList) {
    		try {

    	    	String body = views.html.newsletter.templateNewsletter.render(baseUrl,emailBody, sub.email, getToken(sub.email)).body();
    	    	tools.Util.sendEmailHTML(emailSubject,"Utilisetoncorps.ca <no-reply@utilisetoncorps.ca>",sub.email,body);
    	
    		} catch (Exception e) {
    			return Results.internalServerError();
    		}
    	}
    	
    	
    	return ok();
    }

    
    private static String getToken(String email){
    	return StringUtil.encrypt("SHA1", email , UNSUB_SEED);
    }
}
