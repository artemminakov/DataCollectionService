package controllers;

import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class FieldsController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public FieldsController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result index() {
        return ok(views.html.fields.render());
    }


    /*@Transactional
    public Result addField() {
        Field field = formFactory.form(Field.class).bindFromRequest().get();
        jpaApi.em().persist(field);
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String options = requestData.get("options");
        return ok("Hello " + options);
//        return redirect(routes.MainController.index());
    }*/

    /*@Transactional(readOnly = true)
    public Result getAdmins() {
        List<Admin> admins = (List<Admin>) jpaApi.em().createQuery("select a from Admin a").getResultList();
        return ok(toJson(admins));
    }*/

}
