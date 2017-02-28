package controllers;

import static play.libs.Json.toJson;

import java.util.List;
import javax.inject.Inject;
import models.Admin;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;


/**
 * <tt>AdminsController</tt> controller class, which has methods for interacting
 * with page for creating admin.
 *
 * @author Artem Minakov
 */
public class AdminsController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public AdminsController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render page for adding admin to DB.
     */
    @Security.Authenticated(Secured.class)
    public Result index() {
        return ok(views.html.indexAdmin.render());
    }

    /**
     * Method for adding admin to DB.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result addAdmin() {
        Admin admin = formFactory.form(Admin.class).bindFromRequest().get();
        jpaApi.em().persist(admin);
        return redirect(routes.MainController.index());
    }

    /**
     * Method for selecting from DB all admins.
     */
    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getAdmins() {
        List<Admin> admins = (List<Admin>) jpaApi.em()
                .createQuery("select a from Admin a").getResultList();
        return ok(toJson(admins));
    }
}
