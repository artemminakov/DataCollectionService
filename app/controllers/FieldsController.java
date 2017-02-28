package controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Field;
import models.Response;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.WebSocket;
import security.Secured;

/**
 * <tt>FieldsController</tt> controller class, which has methods for interacting
 * with page which contain all added fields.
 *
 * @author Artem Minakov
 */
public class FieldsController extends Controller {

    private final JPAApi jpaApi;

    private Set<WebSocket.Out<String>> socketsSet = new HashSet<>();

    @Inject
    public FieldsController(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render fields page.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        List<Response> responses = (List<Response>) jpaApi.em()
                .createQuery("select r from Response r").getResultList();
        return ok(views.html.fields.render(fields, responses.size()));
    }

    /**
     * Method for deleting from DB selected field.
     */
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
