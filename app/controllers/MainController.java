package controllers;

import static play.libs.Json.toJson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import models.Field;
import models.Response;
import models.ResponseContent;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;
import security.Secured;

/**
 * <tt>MainController</tt> controller class, which has methods for interacting
 * with page of response(index).
 *
 * @author Artem Minakov
 */
public class MainController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    private Set<Out<String>> socketsSet = new HashSet<>();

    @Inject
    public MainController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render response page.
     */
    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(views.html.index.render(fields, session()));
    }

    /**
     * Method for adding response to DB.
     */
    @Transactional
    public Result addResponse() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        DynamicForm requestData = formFactory.form().bindFromRequest();
        List<ResponseContent> responseContents = new ArrayList<>();
        Response response = new Response();
        String content = "";
        for (Field field : fields) {
            if (field.isActive()) {
                ResponseContent responseContent = new ResponseContent();
                responseContent.setField(field);
                switch (field.getType()) {
                    case SINGLELINETEXT:
                        content = requestData.get(field.getLabel());
                        break;
                    case MULTILINETEXT:
                        content = requestData.get(field.getLabel());
                        break;
                    case RADIOBUTTON:
                        if (field.getOptions() != null) {
                            content = requestData.get("option");
                        }
                        break;
                    case CHECKBOX:
                        if (field.getOptions() != null) {
                            for (String option : field.getOptions().split("\\r?\\n")) {
                                if (requestData.get(option) != null) {
                                    content += option + "\n";
                                }
                            }
                        }
                        break;
                    case COMBOBOX:
                        if (field.getOptions() != null) {
                            content = requestData.get(field.getLabel());
                        }
                        break;
                    case DATE:
                        content = requestData.get(field.getLabel());
                        break;
                }
                responseContent.setContent(content);
                content = "";
                responseContents.add(responseContent);
                jpaApi.em().persist(responseContent);
            }
        }
        response.setResponseContents(responseContents);
        jpaApi.em().persist(response);
        return ok(toJson(response));
    }

    /**
     * Method for selecting from DB all responsesContent(entity which store
     * data of each field from response).
     */
    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getResponseContent() {
        List<ResponseContent> responseContent = (List<ResponseContent>) jpaApi.em()
                .createQuery("select r from ResponseContent r").getResultList();
        return ok(toJson(responseContent));
    }

    /**
     * Method for selecting from DB all responses.
     */
    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getResponses() {
        List<Response> responses = (List<Response>) jpaApi.em()
                .createQuery("select r from Response r").getResultList();
        return ok(toJson(responses));
    }

    /**
     * Method for creating WebSocket index->responses. To send asynchronously
     * response to responses page.
     */
    public LegacyWebSocket<String> createResponseWebSocket() {

        return WebSocket.whenReady((in, out) -> {

            socketsSet.add(out);

            in.onMessage((String response) -> {
                socketsSet.forEach(s -> s.write(response));
            });

            in.onClose(() -> {
                socketsSet.remove(out);
            });
        });
    }

}
