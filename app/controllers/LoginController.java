package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import models.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.loginPage;

/**
 * <tt>LoginController</tt> controller class, which has methods to control
 * admin authentication.
 *
 * @author Artem Minakov
 */
public class LoginController extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;

    private static Map<String, String> users = new HashMap<String, String>();

    @Inject
    public LoginController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    /**
     * Method for render login page.
     */
    public Result login() {
        return ok(loginPage.render());
    }

    /**
     * Method for logout.
     */
    public Result logout() {
        session().clear();
        return redirect(routes.MainController.index());
    }

    /**
     * Method for authenticate user in system.
     */
    @Transactional
    public Result authenticate() {

        //add to Map all users which are registered in system
        List<Admin> admins = (List<Admin>) jpaApi.em()
                .createQuery("select a from Admin a").getResultList();
        admins.stream().forEach(admin ->
                users.put(admin.getLogin(), admin.getPassword()));

        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return redirect(routes.LoginController.login());
        } else {
            session().clear();
            session("login", loginForm.get().getLogin());
            return redirect(routes.MainController.index());
        }
    }

    /**
     * <tt>Login</tt> class for validation admin authenticity.
     *
     * @author Artem Minakov
     */
    public static class Login {

        public String login;
        public String password;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * Method for validating data for login.
         */
        public String validate() {
            return isValidLogin(login, password) ? null : "Invalid user or password";
        }

        private static boolean isValidLogin(String login, String password) {
            return (users.containsKey(login) && users.get(login).equals(password));
        }
    }
}