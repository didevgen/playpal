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
@Transactional
public class LoginController {
    private UserDAO dao = DAOFactory.getDAOFactory().getUserDAO();

    public Result getLoginForm() {
        return ok(login.render(false));
    }

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

    public Result logout() {
        session().clear();
        return redirect("/");
    }
}
