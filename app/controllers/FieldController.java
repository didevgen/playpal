package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import models.entities.Field;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.FieldService;
import services.UserService;
import views.html.createfield;
import views.html.fields;
import java.util.List;
@Transactional
public class FieldController extends Controller {
    private FieldService service = new FieldService();
    private FieldDAO dao = DAOFactory.getDAOFactory(DAOFactory.JPA).getFieldDAO();
    private UserService userService = new UserService();

    public Result getFields() {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        List<Field> fieldList = dao.getAll();
        return ok(fields.render(service.setFieldTypes(fieldList)));
    }

    public Result getFieldForm(int id) {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        Field field = dao.get(id);
        Form<Field> fieldForm = Form.form(Field.class);
        fieldForm.fill(field);
        return ok(createfield.render(fieldForm, field));
    }

    public Result insertField() {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        Field field = new Field();
        field = dao.insert(field);
        return redirect("/fields/" + field.getFieldId());
    }

    public Result updateField() {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        JsonNode json = request().body().asJson();
        Field field = Json.fromJson(json, Field.class);
        dao.update(field);
        return ok();
    }

    public Result deleteField(int id) {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        dao.delete(id);
        return getFields();
    }
}
