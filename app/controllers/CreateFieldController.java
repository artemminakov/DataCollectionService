package controllers;

import models.Field;
import models.Type;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.*;
import security.Secured;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static play.libs.Json.toJson;

public class CreateFieldController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    private Set<WebSocket.Out<String>> socketsSet = new HashSet<>();

    @Inject
    public CreateFieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Security.Authenticated(Secured.class)
    public Result index() {
        List<Type> typesList = Arrays.asList(Type.values());
        return ok(views.html.createField.render(typesList));
    }


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
        //return redirect(routes.CreateFieldController.getFields());
    }

    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getFields() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(toJson(fields));
    }

    public LegacyWebSocket<String> createFieldWebSocket() {

        return WebSocket.whenReady((in, out) -> {

            socketsSet.add(out);

            in.onMessage((String message) -> {
                socketsSet.forEach(s -> s.write(message));
            });

            in.onClose(() -> {
                socketsSet.remove(out);
            });
        });
    }
}