package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import models.Field;
import models.Type;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.FieldService;
import services.UserService;
import validators.CustomError;
import validators.Validatable;
import validators.ValidatorFactory;
import views.html.createfield;
import views.html.errors.error;
import views.html.fields;

import java.util.List;

@Transactional
public class FieldController extends Controller {
    private FieldService service = new FieldService();
    private FieldDAO dao = DAOFactory.getDAOFactory().getFieldDAO();
    private UserService userService = new UserService();
    private Validatable validator = new ValidatorFactory().getValidator(ValidatorFactory.FIELD_VALIDATOR);

    public Result getFields() {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        List<Field> fieldList = dao.getAll();
        return ok(fields.render(service.setFieldTypes(fieldList)));
    }

    public Result getFieldForm(int id) {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        if (id==0) {
            Field field = new Field();
            field.setType(Type.SINGLE_LINE_TEXT);
            return ok(createfield.render(field));
        }
        Field field = dao.get(id);
        if (field==null) {
            return notFound(error.render("Field is not found",404));
        }
        return ok(createfield.render(field));
    }

    public Result updateField() {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        JsonNode json = request().body().asJson();
        Field field = Json.fromJson(json, Field.class);
        List<CustomError> errors = validator.validate(field);
        if (errors.isEmpty()) {
            dao.update(field);
            return ok();
        } else {
            return badRequest(Json.toJson(errors));
        }
    }

    public Result deleteField(int id) {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        dao.delete(id);
        service.checkFieldAmountForResponseDeletion();
        return getFields();
    }
}
