package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import play.db.ebean.Model;

@Entity
public class BlogPost extends Model {
	private static final long serialVersionUID = -8302829775934499318L;

	public static Finder<Long, BlogPost> finder = new Finder<Long, BlogPost>(Long.class, BlogPost.class);

    @Id
    public long id;
    @Lob
    public String title;
    @Lob
    public String body;
    @Lob
    public String bodyHtml;
    public int datePub;
    public boolean isOnline;

    public BlogPost(String title, String body, String bodyHtml,int datePub, boolean isOnline) {
        this.title = title;
        this.body = body;
        this.bodyHtml = bodyHtml;
        this.datePub = datePub;
        this.isOnline = isOnline;
    }
    
    public static void updatePost(BlogPost oldPost, BlogPost newPost){
    	oldPost.title = newPost.title;
    	oldPost.body = newPost.body;
    	oldPost.bodyHtml = newPost.bodyHtml;
    	oldPost.datePub = newPost.datePub;
    	oldPost.isOnline = newPost.isOnline;
    	oldPost.save();
    }
    
    public static BlogPost findBlogById(Long id) {
    	return BlogPost.finder.byId(id);
    	//return null;
    }
}
