import play.*;
import play.libs.F;
import play.mvc.*;
import static play.mvc.Results.*;

public class Global extends GlobalSettings {

    @Override
    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader arg0) {
        return F.Promise.<Result>pure(Results.notFound(views.html.notfound.render()));
    }
    
    /*
    @Override
    public F.Promise<Result> onError(Http.RequestHeader arg0, Throwable arg1) {
        return F.Promise.<Result>pure(Results.internalServerError(views.html.error.render()));
    }
    @Override
    public F.Promise<Result> onBadRequest(Http.RequestHeader arg0, String error) {
        return F.Promise.<Result>pure(Results.badRequest(views.html.error.render()));
    }
    */
    
}