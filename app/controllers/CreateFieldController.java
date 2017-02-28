package controllers;

import static play.libs.Json.toJson;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import models.Field;
import models.Response;
import models.Type;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.WebSocket;
import security.Secured;

/**
 * <tt>CreateFieldController</tt> controller class, which has methods for interacting
 * with createField page.
 *
 * @author Artem Minakov
 */
public class CreateFieldController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    private Set<WebSocket.Out<String>> socketsSet = new HashSet<>();

    @Inject
    public CreateFieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render createField page.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Type> typesList = Arrays.asList(Type.values());
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        List<Response> responses = (List<Response>) jpaApi.em()
                .createQuery("select r from Response r").getResultList();
        return ok(views.html.createField.render(typesList, fields.size(),
                responses.size()));
    }

    /**
     * Method for adding field to DB.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result addField() {
        Field field = formFactory.form(Field.class).bindFromRequest().get();
        String options = "";
        if (field.getType() == Type.SLIDER) {
            DynamicForm requestData = formFactory.form().bindFromRequest();
            options += requestData.get("minvalsl") + "\n";
            options += requestData.get("maxvalsl") + "\n";
            options += requestData.get("stepvalsl");
            field.setOptions(options);
        }
        jpaApi.em().persist(field);
        return ok(toJson(field));
    }

    /**
     * Method for selecting from DB all fields.
     */
    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getFields() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(toJson(fields));
    }

    /**
     * Method for creating WebSocket createField->fields and index. To send
     * asynchronously created field to response and fields pages.
     */
    public LegacyWebSocket<String> createFieldWebSocket() {

        return WebSocket.whenReady((in, out) -> {

            socketsSet.add(out);

            in.onMessage((String field) -> {
                socketsSet.forEach(s -> s.write(field));
            });

            in.onClose(() -> {
                socketsSet.remove(out);
            });
        });
    }
}