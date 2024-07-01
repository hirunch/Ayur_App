package com.s22010120.ayur;

public class OsuInfoDataClass {

    private String osuDataTopic;
    private String osuDataDesc;
    private String osuDataImage;
    private String osuKey;

    public String getOsuKey() {
        return osuKey;
    }

    public void setOsuKey(String osuKey) {
        this.osuKey = osuKey;
    }

    public String getOsuDataTopic() {
        return osuDataTopic;
    }

    public String getOsuDataDesc() {
        return osuDataDesc;
    }

    public String getOsuDataImage() {
        return osuDataImage;
    }

    public OsuInfoDataClass(String osuDataTopic, String osuDataDesc, String osuDataImage) {
        this.osuDataTopic = osuDataTopic;
        this.osuDataDesc = osuDataDesc;
        this.osuDataImage = osuDataImage;
    }

    public OsuInfoDataClass(){

    }




}
