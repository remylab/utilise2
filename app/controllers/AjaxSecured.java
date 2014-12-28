package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

public class AjaxSecured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return Secured.checkUsername(ctx);
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return badRequest();
    }
}