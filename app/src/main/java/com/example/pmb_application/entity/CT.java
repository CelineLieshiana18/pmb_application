package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CT implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String date;
    private String start;
    private String end;
    private String duration;

    public CT() {
    }

    public CT(int id, String name, String description, String date, String start, String end, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.start = start;
        this.end = end;
        this.duration = duration;
    }

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    protected CT(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        date = in.readString();
        start = in.readString();
        end = in.readString();
        duration = in.readString();
    }

    public static final Creator<CT> CREATOR = new Creator<CT>() {
        @Override
        public CT createFromParcel(Parcel in) {
            return new CT(in);
        }

        @Override
        public CT[] newArray(int size) {
            return new CT[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(duration);
    }
}
