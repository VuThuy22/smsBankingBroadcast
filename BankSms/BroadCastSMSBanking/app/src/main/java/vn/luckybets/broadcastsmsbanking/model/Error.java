package vn.luckybets.broadcastsmsbanking.model;

public class Error {
    private int code;
    private String description;
    private boolean action;

    public Error(int code, String description) {
        this.code = code;
        this.description = description;
        this.action = true;
    }

    public Error(int code, String description, boolean action) {
        this.code = code;
        this.description = description;
        this.action = action;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Error{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", action=" + action +
                '}';
    }
}
