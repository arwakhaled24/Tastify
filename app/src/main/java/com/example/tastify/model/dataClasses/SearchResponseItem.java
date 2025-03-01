package com.example.tastify.model.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchResponseItem implements Parcelable {
    @SerializedName("strMeal")
    private String name;

    @SerializedName("strMealThumb")
    private String image;

    public SearchResponseItem(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected SearchResponseItem(Parcel in) {
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<SearchResponseItem> CREATOR = new Creator<SearchResponseItem>() {
        @Override
        public SearchResponseItem createFromParcel(Parcel in) {
            return new SearchResponseItem(in);
        }
        @Override
        public SearchResponseItem[] newArray(int size) {
            return new SearchResponseItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
    }
}
