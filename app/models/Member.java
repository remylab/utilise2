package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import com.avaje.ebean.Ebean;

import play.db.ebean.Model;
import tools.StringUtil;

@Entity
public class Member extends Model {
    private static final long serialVersionUID = 1L;
    private static String PASSWORD_SEED = "iamallaboutthatbassboutthatbass";

    @Id
    public long id;
    @Column(unique = true)
    public String username;
    public String password;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public static Member authenticate(String username, String password) {
        return Ebean.find(Member.class).where().eq("username", username).eq("password", getStoredPassword(password)).findUnique();
    }

    private static String getStoredPassword(String s) {
        return StringUtil.encrypt("SHA1", s, PASSWORD_SEED);
    }
}
