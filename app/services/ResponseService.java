package services;

import models.entities.Answer;
import models.entities.Field;
import models.entities.Option;

import java.util.List;

public class ResponseService {

    private int getFieldId(String key) {
        int startIndex = "field:".length();
        int finalIndex = -1;
        if (key.contains("option:")) {
            finalIndex = key.indexOf(";option:");
        } else {
            finalIndex = key.length();
        }
        return Integer.valueOf(key.substring(startIndex,finalIndex));
    }

    private  int getOptionId(String key) {
        int startIndex = key.indexOf("option:")+"option:".length();
        int finalIndex = key.length();
        return Integer.valueOf(key.substring(startIndex,finalIndex));
    }
    private Field getFieldById(List<Field> fieldList, int fieldId ) {
        for (Field field : fieldList) {
            if (field.getFieldId() == fieldId) {
                return  field;
            }
        }
        return null;
    }
    private Option getOptionById(Field field, int optionId) {
        for (Option option : field.getOptions()) {
            if (option.getOptionId()==optionId) {
                return option;
            }
        }
        return null;
    }
    private Answer getAnswer(List<Field> fieldList, int fieldId, int optionId) {
        Answer answer = new Answer();
        answer.setField(getFieldById(fieldList,fieldId));
        Option option = getOptionById(answer.getField(),optionId);
        if (option !=null) {
            answer.getOptions().add(option);
        }
        return answer;
    }
    public Answer parseAnswer(List<Field> fieldList, String key, String value) {
        Answer answer = getAnswer(fieldList,getFieldId(key),getOptionId(key));
        answer.setValue(value);
        return answer;
    }
}
