package controllers;

import comparators.AnswerComparator;
import constants.CommandContainer;
import constants.Commands;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import models.Answer;
import models.Field;
import models.Response;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ResponseService;
import services.ResponseWrapper;
import services.UserService;
import validators.ValidationError;
import validators.Validatable;
import validators.ValidatorFactory;
import views.html.answer;
import views.html.responses;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for handling the information about response
 */
@Transactional
public class ResponseController extends Controller {
    @Inject
    private ResponseService service;
    @Inject
    private UserService userService;
    private FieldDAO dao = DAOFactory.getDAOFactory().getFieldDAO();
    private ResponseDAO respDao= DAOFactory.getDAOFactory().getResponseDAO();
    private Validatable validator;

    /**
     * Retrieves all existing fields for generating response in page ../responses
     * @return
     */
    public Result getFieldsForResponse() {
        List<Field> fields = dao.getAll();
        fields = fields.stream().filter(item->item.isActive()).collect(Collectors.toList());
        return ok(answer.render(fields));
    }

    /**
     * Validates and persists response data. In success will execute Notify all command
     * @return
     */
    public Result submitFilledForm() {
        DynamicForm dynForm = new DynamicForm().bindFromRequest();
        Map<String, String> map = dynForm.data();
        List<Field> fieldList = dao.getAll();
        Set<Answer> answerSet = new TreeSet<>(new AnswerComparator());
        for (Map.Entry<String, String> elem : map.entrySet()) {
            Answer answer = service.parseAnswer(fieldList, elem.getKey(), elem.getValue());
            answerSet.add(answer);
        }
        List<Answer> result = new ArrayList<>(answerSet);
        Response resp = new Response();
        resp.setAnswers(result);
        validator = new ValidatorFactory().getValidator(ValidatorFactory.RESPONSE_VALIDATOR);
        List<ValidationError> errorList = validator.validate(resp);
        if (errorList.isEmpty()) {
            resp = respDao.insert(resp);
            CommandContainer.getCommand(Commands.NOTIFY_ALL).execute(resp);
            return ok();
        } else {
            return badRequest(Json.toJson(errorList));
        }
    }

    /**
     * Retrieves response list
     * @return
     */
    public  Result getResponses() {
        if(!userService.isAutorized()) {
            return  redirect("/");
        }
        ResponseWrapper wrapper = new ResponseWrapper(true);
        List<Field> fieldList = dao.getAll();
        List<String> headers = new ArrayList<>();
        fieldList.forEach(header->{
            headers.add(header.getLabel());
        });
        return ok(responses.render(wrapper,headers));
    }
}
