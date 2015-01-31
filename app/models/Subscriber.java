package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import tools.Util;

@Entity
public class Subscriber extends Model {
	private static final long serialVersionUID = 3230402639785713805L;

	public static Finder<Long, Subscriber> finder = new Finder<Long, Subscriber>(Long.class, Subscriber.class);
	
    @Id
    public long id;
    @Column(unique = true)
    public String email;
    public Long dateSub;
    
    public Subscriber(String email) {
        this.email = email;
        this.dateSub = Util.getTimeNow();
    }
    
    public static Subscriber addSubscriber(String email) {
    	Subscriber subscriber = new Subscriber(email);
    	subscriber.save();
    	return subscriber;
    }
    
    public static void removeSubscriber(String email) {
    	Subscriber subscriber = finder.where().eq("email", email).findUnique();
    	subscriber.delete();
    }
}
