package util;

import java.util.HashMap;
import java.util.Map;

public class LoginValidator {

    public static boolean isValidLogin(String login, String password) {
        Map<String, String> users = new HashMap<String, String>();
        users.put("admin", "admin");
        return (users.containsKey(login) && users.get(login).equals(password));
    }
}
