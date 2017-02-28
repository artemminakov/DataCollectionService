package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * <tt>SuccessController</tt> controller class, which has method to show
 * congratulatory message to user if all fields on response page was filled
 * right.
 *
 * @author Artem Minakov
 */
public class SuccessController extends Controller {

    /**
     * Method for render page with congratulatory message.
     */
    public Result index() {
        return ok(views.html.success.render());
    }

}
