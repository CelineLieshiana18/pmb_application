package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponseLoginStudent {
    private String status;
    private String message;
    private ArrayList<Student> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Student> getData() {
        return data;
    }

    public void setData(ArrayList<Student> data) {
        this.data = data;
    }
}
