package controllers;

import models.Admin;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.loginPage;

import javax.inject.Inject;

public class LoginController extends Controller {
    private final FormFactory formFactory;

    @Inject
    public LoginController(FormFactory formFactory, JPAApi jpaApi) {
        this.formFactory = formFactory;
    }

    public Result login() {
        return ok(loginPage.render(formFactory.form(Admin.class)));
    }

    public Result logout() {
        session().clear();
        return redirect(routes.MainController.index());
    }

    public Result authenticate() {
        Form<Admin> loginForm = formFactory.form(Admin.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return redirect(routes.LoginController.login());
        } else {
            session().clear();
            session("login", loginForm.get().getLogin());
            return redirect(routes.MainController.index());
        }
    }
}