package comparators;

import models.Answer;

import java.util.Comparator;

public class AnswerComparator implements Comparator<Answer> {
    @Override
    public int compare(Answer o1, Answer o2) {
        if (o1==o2) {
            return 0;
        }
        if (o1.getField().getFieldId() == o2.getField().getFieldId()) {
            o2.getOptions().addAll(o1.getOptions());
            o2.setValue(o2.getValue()+";"+o1.getValue());
            return 0;
        } else {
            return 1;
        }
    }
}
