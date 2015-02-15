package controllers;

import java.util.List;

import org.springframework.util.StringUtils;

import com.avaje.ebean.Ebean;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import tools.StringUtil;
import models.BlogPost;
import models.Subscriber;

public class Newsletter extends Controller {
	private static String UNSUB_SEED = "etsiondormaitsouslesarbres";


    public static Result add(String email) {
    	if ( !StringUtils.isEmpty(email)) {
        	try {
            	Subscriber.addSubscriber(email);
        	} catch (javax.persistence.PersistenceException e) {
        		//ignore
        	}
    	}
    	return ok();
    }
    
    public static Result remove(String email,String token) {
    	String goodToken = getToken(email);
    	if (goodToken.equals(token)) {	
    	}
    	return ok(views.html.admin.unsubscribe.render(email));
    }
    
    @Security.Authenticated(Secured.class)
    public static Result previewNewsletter(Long postId) {

    	BlogPost post = BlogPost.findBlogById(postId); 
    	
    	List<Subscriber> subList = Ebean.find(Subscriber.class).findList();

    	String baseUrl = "http://" + request().host() ;
    	String preview = views.html.newsletter.template.render(baseUrl,post, "email@test.com", "token").body();
    	
    	return ok(views.html.admin.previewnewsletter.render(post,preview));
    }


    @Security.Authenticated(Secured.class)
    public static Result sendNewsletter(Long postId) {

    	BlogPost post = BlogPost.findBlogById(postId); 
    	
    	List<Subscriber> subList = Ebean.find(Subscriber.class).findList();

    	
    	String baseUrl = "http://" + request().host() ;
    	if ( post != null) {
        	for(Subscriber sub : subList) {
        		try {

        	    	String body = views.html.newsletter.template.render(baseUrl,post, sub.email, getToken(sub.email)).body();
        	    	tools.Util.sendEmail("Nouveau billet : " + post.title,"Utilisetoncorps.ca <no-reply@utilisetoncorps.ca>",sub.email,body);
        	
        		} catch (Exception e) {
        			// ignore
        		}
        	}
        	BlogPost.updateNewsletter(post);
        	
        	
        	return ok();
    	}
    	return badRequest("postid not found : " + postId);
    }

    
    private static String getToken(String email){
    	return StringUtil.encrypt("SHA1", email , UNSUB_SEED);
    }
}
