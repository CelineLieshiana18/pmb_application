package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TahunAjaran implements Parcelable {
    private int id;
    private String name;
    private int status;

    public TahunAjaran(int id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public TahunAjaran(TahunAjaran t) {
        this.id = t.id;
        this.name = t.name;
        this.status = t.status;
    }

    protected TahunAjaran(Parcel in) {
        id = in.readInt();
        name = in.readString();
        status = in.readInt();
    }

    public static final Creator<TahunAjaran> CREATOR = new Creator<TahunAjaran>() {
        @Override
        public TahunAjaran createFromParcel(Parcel in) {
            return new TahunAjaran(in);
        }

        @Override
        public TahunAjaran[] newArray(int size) {
            return new TahunAjaran[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(status);
    }
}
