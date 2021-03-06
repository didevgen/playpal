package services;

import models.Answer;
import models.Field;
import models.Option;

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
        if (field==null) {
            return null;
        }
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

    /**
     * Parses answer from the request and converts it into the models.Answer
     * @param fieldList the list of already existing fields
     * @param key - key of the request
     * @param value - value of the requested data
     * @return
     */
    public Answer parseAnswer(List<Field> fieldList, String key, String value) {
        Answer answer = getAnswer(fieldList,getFieldId(key),getOptionId(key));
        answer.setFieldName(key);
        answer.setValue(value);
        return answer;
    }
}
