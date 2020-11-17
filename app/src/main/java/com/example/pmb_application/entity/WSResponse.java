package com.example.pmb_application.entity;

import java.util.ArrayList;

public class WSResponse {
    private String status;
    private String message;
    private ArrayList datas;

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

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas = datas;
    }
}
