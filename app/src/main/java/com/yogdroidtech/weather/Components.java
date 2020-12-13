
package com.yogdroidtech.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Components {

    @SerializedName("co")
    @Expose
    private Double co;
    @SerializedName("no")
    @Expose
    private Double no;
    @SerializedName("no2")
    @Expose
    private Double no2;
    @SerializedName("o3")
    @Expose
    private Double o3;
    @SerializedName("so2")
    @Expose
    private Double so2;
    @SerializedName("pm2_5")
    @Expose
    private Double pm25;
    @SerializedName("pm10")
    @Expose
    private Double pm10;
    @SerializedName("nh3")
    @Expose
    private Double nh3;

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Double getNo() {
        return no;
    }

    public void setNo(Double no) {
        this.no = no;
    }

    public Double getNo2() {
        return no2;
    }

    public void setNo2(Double no2) {
        this.no2 = no2;
    }

    public Double getO3() {
        return o3;
    }

    public void setO3(Double o3) {
        this.o3 = o3;
    }

    public Double getSo2() {
        return so2;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    public Double getPm25() {
        return pm25;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public Double getPm10() {
        return pm10;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getNh3() {
        return nh3;
    }

    public void setNh3(Double nh3) {
        this.nh3 = nh3;
    }

}
