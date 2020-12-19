package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SoalCTIsian implements Parcelable {
    private int id;
    private int number;
    private String question;
    private String score;
    //buat nanti jawaban siswa
    private String jawaban;
    private String user_id;
    private int cts_id;

    public SoalCTIsian(int id, int number, String question, String score, String jawaban, String user_id, int cts_id) {
        this.id = id;
        this.number = number;
        this.question = question;
        this.score = score;
        this.jawaban = jawaban;
        this.user_id = user_id;
        this.cts_id = cts_id;
    }

    public SoalCTIsian(SoalCTIsian isian) {
        this.id = isian.id;
        this.number = isian.number;
        this.question = isian.question;
        this.score = isian.score;
        this.jawaban = isian.jawaban;
        this.user_id = isian.user_id;
        this.cts_id = isian.cts_id;
    }

    protected SoalCTIsian(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        question = in.readString();
        score = in.readString();
        jawaban = in.readString();
        user_id = in.readString();
        cts_id = in.readInt();
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
        dest.writeString(jawaban);
        dest.writeString(user_id);
        dest.writeInt(cts_id);
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

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getCts_id() {
        return cts_id;
    }

    public void setCts_id(int cts_id) {
        this.cts_id = cts_id;
    }
}
