package controllers;

import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import javax.inject.Inject;

public class ResponsesController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public ResponsesController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(views.html.responses.render());
    }

}
