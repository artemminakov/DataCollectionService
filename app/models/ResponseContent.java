package models;

import javax.persistence.*;

@Entity
public class ResponseContent {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int responseContentId;

    @Column
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    private Field field;

    public ResponseContent(String content, Field field) {
        this.content = content;
        this.field = field;
    }

    public ResponseContent() {
    }

    public int getResponseContentId() {
        return responseContentId;
    }

    public void setResponseContentId(int responseContentId) {
        this.responseContentId = responseContentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        StringBuilder responseContent = new StringBuilder();
        responseContent.append("ResponseContent{" +
                "responseContentId=" + responseContentId)
                .append(", content='" + content + '\'')
                .append(", field=" + field + '}');
        return responseContent.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseContent)) return false;

        ResponseContent that = (ResponseContent) o;

        if (responseContentId != that.responseContentId) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return !(field != null ? !field.equals(that.field) : that.field != null);

    }

    @Override
    public int hashCode() {
        int result = responseContentId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (field != null ? field.hashCode() : 0);
        return result;
    }
}
