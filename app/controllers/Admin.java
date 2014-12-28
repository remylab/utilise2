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

public class Admin extends Controller {
	
	private static final DynamicForm form = play.data.Form.form();

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
    
    private static BlogPost getBlogFromRequest(DynamicForm form) {
    	
    	try {

        	String title = form.get("title");
        	String body = form.get("body");
        	String pubdate = form.get("pubdate");
        	
        	String[] dates = pubdate.split(" ");
        	Integer pubday = Integer.parseInt(dates[0]);
        	int pubmonth = Integer.parseInt(dates[1]);
        	int pubyear = Integer.parseInt(dates[2]);
        	
        	Integer date = pubyear * 10000 + pubmonth * 100 + pubday;

        	System.out.println("online :" + form.get("isonline"));
        	boolean isOnline = "true".equals(form.get("isonline"));
        			
        			
        	return new BlogPost(title,body,date,isOnline);	
    	} catch (Exception e) {
    		System.out.println("getBlog error : " +  e.getMessage() );
    	}
    	return null;
    }
}
