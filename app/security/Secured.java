package security;

import play.mvc.Http;
import play.mvc.Security.Authenticator;

/**
 * <tt>Secured</tt> class, for checking user authentication.
 *
 * @author Artem Minakov
 */
public class Secured extends Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("login");
    }

}