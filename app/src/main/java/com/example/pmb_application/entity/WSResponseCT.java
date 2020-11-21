package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponseCT {
    private String status;
    private String message;
    private ArrayList<CT> data;

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

    public ArrayList<CT> getData() {
        return data;
    }

    public void setData(ArrayList<CT> data) {
        this.data = data;
    }
}
