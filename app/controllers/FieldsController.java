package controllers;

import models.Field;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import security.Secured;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldsController extends Controller {

    private final JPAApi jpaApi;

    private Set<WebSocket.Out<String>> socketsSet = new HashSet<>();

    @Inject
    public FieldsController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(views.html.fields.render(fields));
    }

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result deleteField() {
        Map<String, String[]> values = request().body().asFormUrlEncoded();
        Integer id = Integer.parseInt(values.get("id")[0]);
        EntityManager em = jpaApi.em();
        Query query = em.createNativeQuery(
                "DELETE FROM field WHERE fieldId =" + id + ";");
        query.executeUpdate();
        return ok("Field " + id + " was deleted.");
    }

}
