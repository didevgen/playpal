package services;

import com.fasterxml.jackson.databind.JsonNode;
import constants.CommandContainer;
import constants.Commands;
import dao.DAOFactory;
import dao.contract.FieldDAO;
import models.entities.Field;
import models.enums.Type;
import play.Play;

import java.util.List;
import java.util.stream.Collectors;

public class FieldService {
    public List<Field> setFieldTypes(List<Field> src) {
        return src.stream().map(item -> {
            item.setType(Type.valueOf(item.getType().name()));
            return item;
        }).collect(Collectors.toList());
    }
    private boolean isNull(Object obj) {
        return obj==null;
    }

    public void checkFieldAmountForResponseDeletion() {
        DAOFactory fatory = DAOFactory.getDAOFactory();
        if (fatory.getFieldDAO().getFieldsAmount()==0) {
            fatory.getResponseDAO().deleteAll();
            CommandContainer.getCommand(Commands.REFRESH_RESPONSES).execute(null);
        }
    }
}
