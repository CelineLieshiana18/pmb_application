package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponseSoalCTIsian {
    private String status;
    private String message;
    private ArrayList<SoalCTIsian> data;

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

    public ArrayList<SoalCTIsian> getData() {
        return data;
    }

    public void setData(ArrayList<SoalCTIsian> data) {
        this.data = data;
    }
}
