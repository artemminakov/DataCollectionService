package controllers;

import models.Field;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class FieldsController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public FieldsController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em().createQuery("select f from Field f").getResultList();
        return ok(views.html.fields.render(fields));
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
