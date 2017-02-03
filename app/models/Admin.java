package models;

import util.LoginValidator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {

    @Id
    private String login;

    @Column
    private String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Admin(String login) {
        this.login = login;
    }

    public Admin() {
    }

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

    public String validate() {
        return LoginValidator.isValidLogin(login, password) ? null : "Invalid user or password";
    }

    @Override
    public String toString() {
        StringBuilder admin = new StringBuilder();
        admin.append("Admin{login= '" + login + '\'')
                .append(", password= '" + password + '\'' + '}');
        return admin.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;

        Admin admin = (Admin) o;

        if (login != null ? !login.equals(admin.login) : admin.login != null) return false;
        return !(password != null ? !password.equals(admin.password) : admin.password != null);

    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
