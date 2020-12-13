
package com.yogdroidtech.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PollutionData {

    @SerializedName("coordinate")
    @Expose
    private Coordinate coordinate;
    @SerializedName("list")
    @Expose
    private java.util.List<List> list = null;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

}
