package controllers;

import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class ResponsesController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public ResponsesController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result index() {
        return ok(views.html.responses.render());
    }


    /*@Transactional
    public Result addAdmin() {
        Admin admin = formFactory.form(Admin.class).bindFromRequest().get();
        jpaApi.em().persist(admin);
        return redirect(routes.MainController.index());
    }

    @Transactional(readOnly = true)
    public Result getAdmins() {
        List<Admin> admins = (List<Admin>) jpaApi.em().createQuery("select a from Admin a").getResultList();
        return ok(toJson(admins));
    }*/
}