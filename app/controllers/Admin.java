package controllers;

import javax.persistence.PersistenceException;

import models.BlogPost;
import models.Member;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import tools.StringUtil;

public class Admin extends Controller {
	
	private static final DynamicForm form = play.data.Form.form();

    
    public static Result index(int page) {
        Member member = Membership.getUser();
        
        int goPage = Math.max(1, page);
        return ok(views.html.admin.index.render(member,null,goPage));
    }
    
    @Security.Authenticated(AjaxSecured.class)
    public static Result addPost() {
        Member member = Membership.getUser();
        
        DynamicForm dynForm = form.bindFromRequest();
        BlogPost post = getBlogFromRequest(dynForm);
        
        ObjectNode jsonResponse = Json.newObject();

        if ( post != null) {
	        try {
	        	post = Member.addPost(member, post);
	            jsonResponse.put("id", post.id);
	            return ok(jsonResponse);
	        } catch (PersistenceException e) {
	            return internalServerError("DB error");
	        }
        }

        return badRequest("parameter missing");
    }
    
    @Security.Authenticated(AjaxSecured.class)
    public static Result updatePost(Long postId) {
        BlogPost oldPost = BlogPost.finder.byId(postId);
        
        DynamicForm dynForm = form.bindFromRequest();
        BlogPost newPost = getBlogFromRequest(dynForm);
        

        if ( newPost != null) {
	        try {
	        	BlogPost.updatePost(oldPost, newPost);
	            return ok();
	        } catch (PersistenceException e) {
	        	System.out.println("DB error :" + e.getLocalizedMessage());
	            return internalServerError("DB error");
	        }
        }

        return badRequest("parameter missing");
    }
    
    @Security.Authenticated(Secured.class)
    public static Result editPost(Long postId) {
    	

        Member member = Membership.getUser();
    	BlogPost post = BlogPost.finder.byId(postId);
    	
    	return ok(views.html.admin.index.render(member,post,0));
    }
    
    private static BlogPost getBlogFromRequest(DynamicForm form) {
    	
    	try {

        	String title = form.get("title");
        	String body = form.get("body");
        	String bodyhtml = StringUtil.cleanHtml( form.get("bodyhtml") ) ;
        	String pubdate = form.get("pubdate");
        	
        	String[] dates = pubdate.split(" ");
        	Integer pubday = Integer.parseInt(dates[0]);
        	int pubmonth = Integer.parseInt(dates[1]);
        	int pubyear = Integer.parseInt(dates[2]);
        	
        	Integer date = pubyear * 10000 + pubmonth * 100 + pubday;

        	boolean isOnline = "true".equals(form.get("isonline"));

        	return new BlogPost(title,body, bodyhtml, date,isOnline);	
    	} catch (Exception e) {
    		System.out.println("getBlog error : " +  e.getMessage() );
    	}
    	return null;
    }
}
