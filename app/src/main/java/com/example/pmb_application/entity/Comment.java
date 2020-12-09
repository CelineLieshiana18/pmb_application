package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private int id;
    private String description;
    private String date;
    private String student_name;

    public Comment(int id, String date, String description, String student_name) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.student_name = student_name;
    }

    public Comment(Comment c) {
        this.id = c.id;
        this.date = c.date;
        this.description = c.description;
        this.student_name = c.student_name;
    }


    protected Comment(Parcel in) {
        id = in.readInt();
        date = in.readString();
        description = in.readString();
        student_name = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
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
        dest.writeString(description);
        dest.writeString(student_name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
}
