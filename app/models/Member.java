package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import tools.StringUtil;

@Entity
public class Member extends Model {
	private static final long serialVersionUID = -8667761820072628252L;
	private static String PASSWORD_SEED = "iamallaboutthatbassboutthatbass";

    @Id
    public long id;
    @Column(unique = true)
    public String username;
    public String password;
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    public List<BlogPost> posts = new ArrayList<BlogPost>();

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static BlogPost addPost(Member member, BlogPost post) {
        member.posts.add(post);
        member.saveManyToManyAssociations("posts");
        member.save();
        return post;
    }
    
    public static Member authenticate(String username, String password) {
        return Ebean.find(Member.class).where().eq("username", username).eq("password", getStoredPassword(password)).findUnique();
    }

    public static String getStoredPassword(String s) {
        return StringUtil.encrypt("SHA1", s, PASSWORD_SEED);
    }
}
