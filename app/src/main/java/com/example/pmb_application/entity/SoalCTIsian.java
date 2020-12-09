package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SoalCTIsian implements Parcelable {
    private int id;
    private int number;
    private String question;
    private String score;

    public SoalCTIsian(int id, int number, String question, String score) {
        this.id = id;
        this.number = number;
        this.question = question;
        this.score = score;
    }

    public SoalCTIsian(SoalCTIsian isian) {
        this.id = isian.id;
        this.number = isian.number;
        this.question = isian.question;
        this.score = isian.score;
    }

    protected SoalCTIsian(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        question = in.readString();
        score = in.readString();
    }

    public static final Creator<SoalCTIsian> CREATOR = new Creator<SoalCTIsian>() {
        @Override
        public SoalCTIsian createFromParcel(Parcel in) {
            return new SoalCTIsian(in);
        }

        @Override
        public SoalCTIsian[] newArray(int size) {
            return new SoalCTIsian[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(number);
        dest.writeString(question);
        dest.writeString(score);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
