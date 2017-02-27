package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int responseId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ResponseContent> responseContents;

    public Response(List<ResponseContent> responseContents) {
        this.responseContents = responseContents;
    }

    public Response() {
    }

    public int getResponseId() {
        return responseId;
    }

    public List<ResponseContent> getResponseContents() {
        return responseContents;
    }

    public void setResponseContents(List<ResponseContent> responseContents) {
        this.responseContents = responseContents;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder();
        response.append("Response{ responseId= " + responseId)
                .append(", responseContents= " + responseContents + '}');
        return response.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Response)) {
            return false;
        }

        Response response = (Response) o;

        if (responseId != response.responseId) {
            return false;
        }
        return !(responseContents != null ? !responseContents.equals(response.responseContents)
                : response.responseContents != null);

    }

    @Override
    public int hashCode() {
        int result = responseId;
        result = 31 * result + (responseContents != null ? responseContents.hashCode() : 0);
        return result;
    }
}
