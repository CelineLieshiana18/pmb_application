package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private int id;
    private String nrp;
    private String name;
    private String password;
    private String gender;
    private String email;
    private String roles_name;

    public Student() {
    }


    public Student(Student student) {
        this.id = student.id;
        this.nrp = student.nrp;
        this.name = student.name;
        this.password = student.password;
        this.gender = student.gender;
        this.email = student.email;
        this.roles_name = student.roles_name;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        nrp = in.readString();
        name = in.readString();
        password = in.readString();
        gender = in.readString();
        email = in.readString();
        roles_name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
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
        dest.writeString(roles_name);
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

    public String getRoles_name() {
        return roles_name;
    }

    public void setRoles_name(String roles_name) {
        this.roles_name = roles_name;
    }
}
