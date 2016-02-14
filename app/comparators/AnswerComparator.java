package comparators;

import models.Answer;

import java.util.Comparator;

/**
 * Comparator for TreeSet<Answer>. Was created because of changing values of Answers during the inserting new values
 * I.E. in case of having field with multiple choice the new value must be added to the previous.
 */
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
