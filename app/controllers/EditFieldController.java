package controllers;


import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Field;
import models.Type;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import security.Secured;

/**
 * <tt>EditFieldController</tt> controller class, which has methods for interacting
 * with editField page.
 *
 * @author Artem Minakov
 */
public class EditFieldController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;
    private Field fieldForEdit;

    @Inject
    public EditFieldController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render editField page.
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public Result index(String fieldId) {
        fieldForEdit = (Field) jpaApi.em()
                .createQuery("select f from Field f where fieldId = " + fieldId)
                .getSingleResult();
        List<Type> typesList = Arrays.asList(Type.values());
        return ok(views.html.editField.render(typesList, fieldForEdit));
    }

    /**
     * Method for editing field in DB.
     */
    @Transactional
    public Result editField() {
        Field field = formFactory.form(Field.class).bindFromRequest().get();
        String options = "";
        if (field.getType() == Type.SLIDER) {
            DynamicForm requestData = formFactory.form().bindFromRequest();
            options += requestData.get("minvalsl") + "\n";
            options += requestData.get("maxvalsl") + "\n";
            options += requestData.get("stepvalsl");
            field.setOptions(options);
        }

        EntityManager em = jpaApi.em();
        Query query = em.createNativeQuery("UPDATE field SET " +
                "label = '" + field.getLabel() + "', " +
                "type = '" + field.getType() + "', " +
                "isRequired = " + field.isRequired() + ", " +
                "isActive = " + field.isActive() + ", " +
                "options = '" + field.getOptions() + "' " +
                "WHERE fieldId = " + fieldForEdit.getFieldId() + ";");
        query.executeUpdate();
        return redirect(routes.FieldsController.index());
    }
}
