package com.yxf.bindercode;

import android.os.Parcel;
import android.os.Parcelable;

public class CarData implements Parcelable {
    private int id;
    private String name;
    private int price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public CarData(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    protected CarData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readInt();
    }

    public static final Creator<CarData> CREATOR = new Creator<CarData>() {
        @Override
        public CarData createFromParcel(Parcel in) {
            return new CarData(in);
        }

        @Override
        public CarData[] newArray(int size) {
            return new CarData[size];
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
        dest.writeInt(price);
    }
}
