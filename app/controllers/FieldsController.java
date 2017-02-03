package controllers;

import models.Field;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

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

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em().createQuery("select f from Field f").getResultList();
        return ok(views.html.fields.render(fields));
    }


}
