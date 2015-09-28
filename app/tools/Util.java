package tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import models.BlogPost;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.mail.EmailAttachment;

import play.libs.mailer.Email;
import play.libs.mailer.MailerPlugin;

public class Util {
	
	public static class BlogResult {
		public List<BlogPost> posts;
		public int page;
		public int total;
		public int paginationFirst;
		public int paginationLast;
		
		public BlogResult(List<BlogPost> posts, int page, int total, int first, int last) {
			this.posts = posts;
			this.page = page;
			this.total = total;
			this.paginationFirst = first;
			this.paginationLast = last;
		}
	}
	public static BlogResult getBlogResult(int pageSize, int numPage) {
		return getBlogResult(pageSize,numPage,true);
	}
    public static BlogResult getBlogResult(int pageSize, int numPage, boolean withOffline) {
    	
        PagingList<BlogPost> pagingList = null;
        if ( withOffline ) {
        	pagingList = Ebean.find(BlogPost.class).orderBy("datePub desc, id desc").findPagingList(pageSize);
        } else {
        	pagingList = Ebean.find(BlogPost.class).where().eq("isOnline", true).orderBy("datePub desc, id desc").findPagingList(pageSize);
        }
        if ( pagingList != null) {
            pagingList.getFutureRowCount();
            
            Page<BlogPost> page = pagingList.getPage(numPage-1);
            int totalPages = page.getTotalPageCount(); 
            int currentPage = page.getPageIndex()+1;
            
            int numPages = 7;
            int first = Math.max(1, currentPage-3);
            int last = Math.min(totalPages, currentPage+3);
            
            if ( (last-first+1)< numPages ) {
            	last = Math.min(totalPages, last + (numPages-last+first-1) );
            }

            if ( (last-first+1) < numPages ) {
            	first = Math.max(1, first - (numPages-last+first-1));
            }
            
            return new BlogResult(page.getList(), currentPage,totalPages,first,last);
        }
        return null;
    }
        
    public static boolean sendEmail(String subject,String fromEmail, String toEmail, String body) {
        final Email email = new Email();
        email.setSubject(subject);
        email.setFrom(fromEmail);
        email.addTo(toEmail);
        //email.addAttachment("favicon.png", new File(Play.application().classloader().getResource("public/images/favicon.png").getPath()));
        //email.addAttachment("data.txt", "data".getBytes(), "text/plain", "Simple data", EmailAttachment.INLINE);

        email.setBodyHtml(body);
        email.setBodyText(StringEscapeUtils.escapeHtml4(body));

        
        try {
        	MailerPlugin.send(email);
        	return true;
        } catch (Exception e) {
        	return false;
        }
    }
    
    public static Long getTimeNow() {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return calendar.getTime().getTime();
    }

    
    public static Integer getTimeNowForDB() {
    	final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String sDate = sdf.format(getTimeNow());
        
        return Integer.parseInt(sDate);
    }

    public static String getDate(Long time) {

        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z");

        // Give it to me in GMT time.
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(time);

    }
    
}
