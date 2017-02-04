package controllers;

import models.Field;
import models.Response;
import models.ResponseContent;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

public class MainController extends Controller {
    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    @Inject
    public MainController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em()
                .createQuery("select f from Field f").getResultList();
        return ok(views.html.index.render(fields, session()));
    }


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
                            for (String option : field.getOptions().split("\\r?\\n")) {
                                if (requestData.get(option) == "true") {
                                    content += option + "\n";
                                }
                            }
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
                            for (String option : field.getOptions().split("\\r?\\n")) {
                                content = option;
                            }
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
        return redirect(routes.MainController.index());
    }

    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getResponseContent() {
        List<ResponseContent> responseContent = (List<ResponseContent>) jpaApi.em()
                .createQuery("select r from ResponseContent r").getResultList();
        return ok(toJson(responseContent));
    }

    @Security.Authenticated(Secured.class)
    @Transactional(readOnly = true)
    public Result getResponseContents() {
        Response response = (Response) jpaApi.em()
                .createQuery("select r from Response r").getSingleResult();
        return ok(toJson(response));
    }

}
