package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SoalCTPilihanGanda implements Parcelable {
    private int id;
    private int number;
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String key;

    public SoalCTPilihanGanda(int id, int number, String question, String a, String b, String c, String d, String e, String key) {
        this.id = id;
        this.number = number;
        this.question = question;
        A = a;
        B = b;
        C = c;
        D = d;
        E = e;
        this.key = key;
    }

    public SoalCTPilihanGanda(SoalCTPilihanGanda pg) {
        this.id = pg.id;
        this.number = pg.number;
        this.question = pg.question;
        A = pg.A;
        B = pg.B;
        C = pg.C;
        D = pg.D;
        E = pg.E;
        this.key = pg.key;
    }

    protected SoalCTPilihanGanda(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        question = in.readString();
        A = in.readString();
        B = in.readString();
        C = in.readString();
        D = in.readString();
        E = in.readString();
        key = in.readString();
    }

    public static final Creator<SoalCTPilihanGanda> CREATOR = new Creator<SoalCTPilihanGanda>() {
        @Override
        public SoalCTPilihanGanda createFromParcel(Parcel in) {
            return new SoalCTPilihanGanda(in);
        }

        @Override
        public SoalCTPilihanGanda[] newArray(int size) {
            return new SoalCTPilihanGanda[size];
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
        dest.writeString(A);
        dest.writeString(B);
        dest.writeString(C);
        dest.writeString(D);
        dest.writeString(E);
        dest.writeString(key);
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

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getE() {
        return E;
    }

    public void setE(String e) {
        E = e;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
