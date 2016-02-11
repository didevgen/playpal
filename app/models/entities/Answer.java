package models.entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="answer")
public class Answer {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "answer_id")
    private Integer answerId;

    @ManyToOne()
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToMany(mappedBy="answerList")
    private List<Option> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    private Response response;

    @Column(name="value")
    private String value;
    @Transient
    private String fieldName;
    public Answer() {
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (!answerId.equals(answer.answerId)) return false;
        return !(value != null ? !value.equals(answer.value) : answer.value != null);

    }

    @Override
    public int hashCode() {
        int result = answerId.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
