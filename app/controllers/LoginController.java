package controllers;

import dao.DAOFactory;
import dao.contract.UserDAO;
import models.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Result;
import views.html.login;

import java.security.NoSuchAlgorithmException;

import static play.mvc.Controller.session;
import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

/**
 * Controller method for manipulating user data
 */
@Transactional
public class LoginController {
    private UserDAO dao = DAOFactory.getDAOFactory().getUserDAO();

    /**
     * Renders the login form without error list
     * @return
     */
    public Result getLoginForm() {
        return ok(login.render(false));
    }

    /**
     * Checks existing user in the database. If user exists it will redirect to the ../fields
     * Otherwise will be refreshed with error message
     * @return
     * @throws NoSuchAlgorithmException
     */
    public Result submitLoginForm() throws NoSuchAlgorithmException {
        Form<User> form = new Form<User>(User.class).bindFromRequest();
        User user = form.get();
        User userResult = dao.getUserByLoginAndPassword(user.getLogin(),user.getPassword());
        if (userResult==null) {
            return ok(login.render(true));
        } else {
            session("user",userResult.getLogin());
            return redirect("/fields");
        }
    }

    /**
     * Invalidates session
     * @return
     */
    public Result logout() {
        session().clear();
        return redirect("/");
    }
}
