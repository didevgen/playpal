package models.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "field_option")
public class Option {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "option_id")
    private Integer optionId;

    @Column(name = "option_value")
    private String optionValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "option_answer",
            joinColumns = {@JoinColumn(name = "option_id")},
            inverseJoinColumns = {@JoinColumn(name = "answer_id")})
    private List<Answer> answerList = new ArrayList<Answer>();

    public Option() {
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        if (optionId != null ? !optionId.equals(option.optionId) : option.optionId != null) return false;
        return !(optionValue != null ? !optionValue.equals(option.optionValue) : option.optionValue != null);

    }

    @Override
    public int hashCode() {
        int result = optionId != null ? optionId.hashCode() : 0;
        result = 31 * result + (optionValue != null ? optionValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionValue='" + optionValue + '\'' +
                '}';
    }
}
