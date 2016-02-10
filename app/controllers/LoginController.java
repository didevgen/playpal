package controllers;

import dao.DAOFactory;
import dao.contract.FieldDAO;
import dao.contract.UserDAO;
import models.entities.User;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.UserService;
import views.html.login;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
