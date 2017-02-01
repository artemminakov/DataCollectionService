package security;

import play.mvc.Http;
import play.mvc.Security.Authenticator;

public class Secured extends Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("login");
    }

}
