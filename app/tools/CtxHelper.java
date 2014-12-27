package tools;

import models.Member;
import play.mvc.Http;
//import action.MemberAction;

public class CtxHelper {

    /**
     * How to use in the view :
     * 
     * @import tools._
     * 
     * @for(group <- CHelper.groups() ) { @group.name }
     * 
     */
    public static Member member() {
        return (Member) Http.Context.current().args.get("MemberAction.MEMBER");
    }

    public static boolean getBool(String key) {
        Object val = Http.Context.current().args.get(key);
        if (val != null) {
            return (Boolean) Http.Context.current().args.get(key);
        }
        return false;
    }

    public static void setBool(String key, Boolean value) {
        Http.Context.current().args.put(key, value);
    }
}
