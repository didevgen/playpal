package models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="response")
public class Response {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "response_id")
    private Integer responseId;

    @OneToMany(mappedBy="response", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    public Response() {
    }

    public Integer getResponseId() {
        return responseId;
    }

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return !(responseId != null ? !responseId.equals(response.responseId) : response.responseId != null);

    }

    @Override
    public int hashCode() {
        return responseId != null ? responseId.hashCode() : 0;
    }
}
