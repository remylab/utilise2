package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import tools.StringUtil;

@Entity
public class BlogPost extends Model {
    private static final long serialVersionUID = 1L;

    @Id
    public long id;
    public String title;
    public String body;
    public int datePub;
    public boolean isOnline;

    public BlogPost(String title, String body,int datePub, boolean isOnline) {
        this.title = title;
        this.body = body;
        this.datePub = datePub;
        this.isOnline = isOnline;
    }
}
