package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Mahasiswa implements Parcelable {
    private int id;
    private String nrp;
    private String name;
    private String password;
    private String gender;
    private String email;
    private int years_id;

    public Mahasiswa() {
    }


    protected Mahasiswa(Parcel in) {
        id = in.readInt();
        nrp = in.readString();
        name = in.readString();
        password = in.readString();
        gender = in.readString();
        email = in.readString();
        years_id = in.readInt();
    }

    public static final Creator<Mahasiswa> CREATOR = new Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel in) {
            return new Mahasiswa(in);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nrp);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(gender);
        dest.writeString(email);
        dest.writeInt(years_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYears_id() {
        return years_id;
    }

    public void setYears_id(int years_id) {
        this.years_id = years_id;
    }
}
