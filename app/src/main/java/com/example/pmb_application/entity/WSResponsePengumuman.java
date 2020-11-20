package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponsePengumuman {
    private String status;
    private String message;
    private ArrayList<Pengumuman> data;

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

    public ArrayList<Pengumuman> getData() {
        return data;
    }

    public void setData(ArrayList<Pengumuman> data) {
        this.data = data;
    }
}
