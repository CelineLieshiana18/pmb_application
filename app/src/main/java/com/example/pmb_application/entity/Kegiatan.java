package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Kegiatan implements Parcelable {
    private int id;
    private String date;
    private String start;
    private String end;
    private String place;
    private String pic;
    private String description;

    public Kegiatan() {
    }

    public Kegiatan(int id, String date, String start, String end, String place, String pic, String description) {
        this.id = id;
        this.date = date;
        this.start = start;
        this.end = end;
        this.place = place;
        this.pic = pic;
        this.description = description;
    }

    protected Kegiatan(Parcel in) {
        id = in.readInt();
        date = in.readString();
        start = in.readString();
        end = in.readString();
        place = in.readString();
        pic = in.readString();
        description = in.readString();
    }

    public static final Creator<Kegiatan> CREATOR = new Creator<Kegiatan>() {
        @Override
        public Kegiatan createFromParcel(Parcel in) {
            return new Kegiatan(in);
        }

        @Override
        public Kegiatan[] newArray(int size) {
            return new Kegiatan[size];
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
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(place);
        dest.writeString(pic);
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
