package errors;
import play.*;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.F;
import play.libs.F.*;
import play.mvc.Http.*;
import play.mvc.*;
import views.html.errors.error;
import views.html.errors.servererror;

import javax.inject.*;
public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment,
                            OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

//    @Override
//    public Promise<Result> onServerError(RequestHeader request, Throwable exception) {
//        return Promise.<Result>pure(Results.ok(servererror. render()));
//    }

    @Override
    protected Promise<Result> onNotFound(RequestHeader request, String message) {
        return Promise.<Result>pure(Results.ok(error.render("Not Found", 404))
        );
    }

    @Override
    protected Promise<Result> onBadRequest(RequestHeader request, String message) {
        return Promise.<Result>pure(Results.ok(error.render("Bad request", 400))
        );
    }

    @Override
    protected Promise<Result> onForbidden(RequestHeader request, String message) {
        return Promise.<Result>pure(Results.ok(error.render("Forbidden", 403))
        );
    }


    @Override
    public Promise<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return Promise.<Result>pure(Results.ok(servererror.render()));
    }

}
