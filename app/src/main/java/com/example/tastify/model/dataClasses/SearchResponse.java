package com.example.tastify.model.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchResponse implements Parcelable {
    @SerializedName("meals")
    private List<SearchResponseItem> meals;

    public List<SearchResponseItem> getMeals() {
        return meals;
    }

    SearchResponse(){}
    protected SearchResponse(Parcel in) {
        meals = in.createTypedArrayList(SearchResponseItem.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(meals);
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel in) {
            return new SearchResponse(in);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };
}
