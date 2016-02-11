package controllers;

import comparators.AnswerComparator;
import constants.CommandContainer;
import constants.Commands;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import models.entities.Answer;
import models.entities.Field;
import models.entities.Response;
import play.data.DynamicForm;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ResponseService;
import services.ResponseWrapper;
import services.UserService;
import validators.CustomError;
import validators.Validatable;
import validators.ValidatorFactory;
import views.html.answer;
import views.html.responses;

import java.util.*;
@Transactional
public class ResponseController extends Controller {
    private ResponseService service = new ResponseService();
    private UserService userService = new UserService();
    private FieldDAO dao = DAOFactory.getDAOFactory().getFieldDAO();
    private ResponseDAO respDao= DAOFactory.getDAOFactory().getResponseDAO();
    private Validatable validator = new ValidatorFactory().getValidator(ValidatorFactory.RESPONSE_VALIDATOR);

    public Result getFieldsForResponse() {
        return ok(answer.render(dao.getAll()));
    }

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
        List<CustomError> errorList = validator.validate(resp);
        if (errorList.isEmpty()) {
            resp = respDao.insert(resp);
            CommandContainer.getCommand(Commands.NOTIFY_ALL).execute(resp);
            return ok();
        } else {
            return badRequest(Json.toJson(errorList));
        }
    }

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
