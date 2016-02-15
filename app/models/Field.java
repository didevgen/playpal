package models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;


import java.util.ArrayList;
import java.util.List;

/**
 Application is designed to collect data from users. Form for data collection will have generic number of fields. Field is an entity from database. Field has next fields:
 Label - string
 Type - enumeration (single line text, multi line text, radio button, check box, combo box, date, slider)
 Required - boolean (response to this field will be required)
 Is Active - boolean (user make field not active for any reason, it will remove field from the form).
 */
@Entity
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "field_id")
    private Integer fieldId;

    @Column(name = "label")
    private String label;

    @Column(name = "required")
    private boolean required;

    @Column(name ="active")
    private boolean isActive;

    @Column(name ="type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy="field", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answerList = new ArrayList<>();

    @OneToMany(mappedBy="field", fetch = FetchType.EAGER ,cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE})
    private List<Option> options = new ArrayList<>();

    public Field() {
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(this.fieldId).append("; ").append(this.getLabel()).append("; ");
        sb.append(this.getType().getValue()).append("; ");
        for(Option opt : this.getOptions()) {
            sb.append(opt.getOptionValue()).append("; ");
        }
        sb.append("]");
        return sb.toString();
    }
}
