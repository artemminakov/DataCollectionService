package models;

import javax.persistence.*;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int optionId;

    @Column
    private String content;

    @Column
    private boolean isSelected;

    @ManyToOne(cascade = CascadeType.ALL)
    private ResponseContent responseContent;

    public Option(String content, boolean isSelected, ResponseContent responseContent) {
        this.content = content;
        this.isSelected = isSelected;
        this.responseContent = responseContent;
    }

    public Option() {
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ResponseContent getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(ResponseContent responseContent) {
        this.responseContent = responseContent;
    }

    @Override
    public String toString() {
        StringBuilder option = new StringBuilder();
        option.append("Option{ optionId=" + optionId)
                .append(", content='" + content + '\'')
                .append(", isSelected=" + isSelected)
                .append(", responseContent=" + responseContent + '}');
        return option.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;

        Option option = (Option) o;

        if (optionId != option.optionId) return false;
        if (isSelected != option.isSelected) return false;
        if (content != null ? !content.equals(option.content) : option.content != null) return false;
        return !(responseContent != null ? !responseContent.equals(option.responseContent) : option.responseContent != null);

    }

    @Override
    public int hashCode() {
        int result = optionId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (isSelected ? 1 : 0);
        result = 31 * result + (responseContent != null ? responseContent.hashCode() : 0);
        return result;
    }
}
