package com.example.ls_itunes;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ITunesResponse<T> {

    @SerializedName("resultCount")
    private int resultCount;

    @SerializedName("results")
    private List<T> results;

    public int getResultCount() {
        return resultCount;
    }

    public List<T> getResults() {
        return results;
    }
}
