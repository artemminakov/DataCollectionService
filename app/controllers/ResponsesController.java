package controllers;

import java.util.List;
import javax.inject.Inject;
import models.Field;
import models.Response;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

/**
 * <tt>ResponsesController</tt> controller class, which has method to show
 * table with responses which are submitted by users.
 *
 * @author Artem Minakov
 */
public class ResponsesController extends Controller {

    private final JPAApi jpaApi;

    @Inject
    public ResponsesController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render page with responses.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Response> responses = (List<Response>) jpaApi.em()
                .createQuery("select r from Response r").getResultList();
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(views.html.responses.render(responses, fields));
    }

}
