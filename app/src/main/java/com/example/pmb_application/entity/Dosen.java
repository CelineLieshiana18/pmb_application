package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Dosen  implements Parcelable {
    private int id;
    private String nip;
    private String name;
    private String email;
    private int status;
    private String genre;
    private String jabatan;
    private String created_at;
    private String updated_at;

    public Dosen(int id, String nip, String name, String email, int status, String genre, String jabatan, String created_at, String updated_at) {
        this.id = id;
        this.nip = nip;
        this.name = name;
        this.email = email;
        this.status = status;
        this.genre = genre;
        this.jabatan = jabatan;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    protected Dosen(Parcel in) {
        id = in.readInt();
        nip = in.readString();
        name = in.readString();
        email = in.readString();
        status = in.readInt();
        genre = in.readString();
        jabatan = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<Dosen> CREATOR = new Creator<Dosen>() {
        @Override
        public Dosen createFromParcel(Parcel in) {
            return new Dosen(in);
        }

        @Override
        public Dosen[] newArray(int size) {
            return new Dosen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nip);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeInt(status);
        dest.writeString(genre);
        dest.writeString(jabatan);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}