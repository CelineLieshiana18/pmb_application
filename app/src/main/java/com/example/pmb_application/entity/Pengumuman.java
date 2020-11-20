package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Pengumuman implements Parcelable {
    private int id;
    private String student_name;
    private String date;
    private String description;

    public Pengumuman() {
    }

    protected Pengumuman(Parcel in) {
        id = in.readInt();
        student_name = in.readString();
        date = in.readString();
        description = in.readString();
    }

    public static final Creator<Pengumuman> CREATOR = new Creator<Pengumuman>() {
        @Override
        public Pengumuman createFromParcel(Parcel in) {
            return new Pengumuman(in);
        }

        @Override
        public Pengumuman[] newArray(int size) {
            return new Pengumuman[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(student_name);
        dest.writeString(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeskripsi() {
        return description;
    }

    public void setDeskripsi(String deskripsi) {
        this.description = deskripsi;
    }


    public String getNama_user() {
        return student_name;
    }

    public void setNama_user(String nama_user) {
        this.student_name = nama_user;
    }
}
