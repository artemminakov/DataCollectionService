package controllers;

import models.Field;
import models.Type;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static play.libs.Json.toJson;

public class CreateFieldController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public CreateFieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    public Result index() {
        List<Type> typesList = Arrays.asList(Type.values());
        return ok(views.html.createField.render(typesList));
    }


    @Transactional
    public Result addField() {
        Field field = formFactory.form(Field.class).bindFromRequest().get();
        jpaApi.em().persist(field);
        return redirect(routes.CreateFieldController.index());
    }

    @Transactional(readOnly = true)
    public Result getFields() {
        List<Field> fields = (List<Field>) jpaApi.em().createQuery("select f from Field f").getResultList();
        return ok(toJson(fields));
    }
}