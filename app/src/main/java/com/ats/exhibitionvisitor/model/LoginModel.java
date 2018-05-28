package com.ats.exhibitionvisitor.model;

public class LoginModel {

    private Boolean error;
    private String msg;
    private Visitor visitor;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                ", visitor=" + visitor +
                '}';
    }
}
