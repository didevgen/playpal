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
import validators.ValidationError;
import validators.Validatable;
import validators.ValidatorFactory;
import views.html.createfield;
import views.html.errors.error;
import views.html.fields;

import javax.inject.Inject;
import java.util.List;

/**
 * Controller class for handling requests for field data
 */
@Transactional
public class FieldController extends Controller {
    private Validatable validator;
    private FieldDAO dao = DAOFactory.getDAOFactory().getFieldDAO();

    @Inject
    private FieldService service;
    @Inject
    private UserService userService;

    /**
     * Gets all fields from the database
     * @return rendering to the ../fields page
     */
    public Result getFields() {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        List<Field> fieldList = dao.getAll();
        return ok(fields.render(service.setFieldTypes(fieldList)));
    }

    /**
     * Method for updating/creating new field. (For updating will give field from the database)
     * @param id - 0 means insert another - update. If field doesn't exist - shows 404 error page
     * @return
     */
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

    /**
     * Method for handling form submission
     * In case of validation fails will return badRequest
     * @return
     */
    public Result updateField() {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        JsonNode json = request().body().asJson();
        Field field = Json.fromJson(json, Field.class);
        validator = new ValidatorFactory().getValidator(ValidatorFactory.FIELD_VALIDATOR);
        List<ValidationError> errors = validator.validate(field);
        if (errors.isEmpty()) {
            dao.update(field);
            return ok();
        } else {
            return badRequest(Json.toJson(errors));
        }
    }

    /**
     * Deletes field from the database. Will delete all responses in case of zero amount of fields
     * @param id - identifier of the field
     * @return
     */
    public Result deleteField(int id) {
        if (!userService.isAutorized()) {
            return redirect("/");
        }
        dao.delete(id);
        service.checkFieldAmountForResponseDeletion();
        return getFields();
    }
}
