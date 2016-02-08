package controllers;

import commands.Command;
import comparators.AnswerComparator;
import constants.CommandContainer;
import constants.Commands;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import dao.contract.ResponseDAO;
import models.entities.Answer;
import models.entities.Field;
import models.entities.Option;
import models.entities.Response;
import play.data.DynamicForm;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.ResponseService;
import services.ResponseWrapper;
import services.UserService;
import views.html.answer;
import views.html.index;
import views.html.responses;

import java.util.*;
@Transactional
public class ResponseController extends Controller {
    private ResponseService service = new ResponseService();
    private UserService userService = new UserService();
    private FieldDAO dao = DAOFactory.getDAOFactory(DAOFactory.JPA).getFieldDAO();
    private ResponseDAO respDao= DAOFactory.getDAOFactory(DAOFactory.JPA).getResponseDAO();

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
        resp = respDao.insert(resp);
        CommandContainer.getCommand(Commands.NOTIFY_ALL).execute(resp);
        return ok();
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
