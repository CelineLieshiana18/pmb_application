package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponseForum {
    private String status;
    private String message;
    private ArrayList<Forum> data;

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

    public ArrayList<Forum> getData() {
        return data;
    }

    public void setData(ArrayList<Forum> data) {
        this.data = data;
    }
}
