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

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index() {
        List<Field> fields = (List<Field>) jpaApi.em().createQuery("select f from Field f").getResultList();
        return ok(views.html.index.render(fields));
    }


    @Transactional
    public Result addResponse() {
        List<Field> fields = (List<Field>) jpaApi.em().createQuery("select f from Field f").getResultList();
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String anwer = "";
        String content = "";
        for (Field field : fields) {
            if (field.isActive()) {
                ResponseContent responseContent = new ResponseContent();
                responseContent.setField(field);
                switch (field.getType()) {
                    case SINGLELINETEXT:
                        content = requestData.get(field.getLabel());
                    case RADIOBUTTON:
                        if (field.getOptions() != null) {
                            anwer += requestData.get("Male") + "\n";
                            for (String option : field.getOptions().split("\\r?\\n")) {
                                if (requestData.get(option) == "true") {
                                    content += option + "\n";
//                                    anwer += option;
                                }
                            }
                        }
                }
                responseContent.setContent(content);
                jpaApi.em().persist(responseContent);
            }
        }
        return ok(anwer);
//        return redirect(routes.MainController.index());
    }

    @Transactional(readOnly = true)
    public Result getResponseContent() {
        List<ResponseContent> responseContent = (List<ResponseContent>) jpaApi.em()
                .createQuery("select r from ResponseContent r").getResultList();
        return ok(toJson(responseContent));
    }

}
