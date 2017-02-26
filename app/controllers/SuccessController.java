package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class SuccessController extends Controller {

    public Result index() {
        return ok(views.html.success.render());
    }

}
