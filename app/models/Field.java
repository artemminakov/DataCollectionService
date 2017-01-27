package models;

import play.data.validation.Constraints;

import javax.persistence.*;

@Entity
public class Field {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int fieldId;

    @Column
    @Constraints.Required
    private String label;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    private boolean isRequired;

    @Column
    private boolean isActive;

    @Column
    private String options;

    public Field(String label, Type type, boolean isRequired, boolean isActive) {
        this.label = label;
        this.type = type;
        this.isRequired = isRequired;
        this.isActive = isActive;
    }

    public Field() {
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        StringBuilder field = new StringBuilder();
        field.append("Field{fieldId= " + fieldId)
                .append(", label= '" + label + '\'')
                .append(", type= " + type)
                .append(", isRequired= " + isRequired)
                .append(", isActive= " + isActive)
                .append(", options= " + options + '}');
        return field.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field)) return false;

        Field field = (Field) o;

        if (fieldId != field.fieldId) return false;
        if (isRequired != field.isRequired) return false;
        if (isActive != field.isActive) return false;
        if (!label.equals(field.label)) return false;
        return type == field.type;

    }

    @Override
    public int hashCode() {
        int result = fieldId;
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
