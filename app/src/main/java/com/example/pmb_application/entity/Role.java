package com.example.pmb_application.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Role implements Parcelable {
    private int id;
    private String name;

    public Role(Role r) {
        this.id = r.id;
        this.name = r.name;
    }

    protected Role(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Role> CREATOR = new Creator<Role>() {
        @Override
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        @Override
        public Role[] newArray(int size) {
            return new Role[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
    @Override
    public String toString() {
        return  id +" -- " + name ;
    }
}
