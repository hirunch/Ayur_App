package com.s22010120.ayur;

public class OHelpsDataClass {
    private String otherHImage;
    private String otherHTopic;
    private String otherHDesc;
    private String ohKey;

    public String getOhKey() {
        return ohKey;
    }

    public void setOhKey(String ohKey) {
        this.ohKey = ohKey;
    }

    public String getOtherHTopic() {
        return otherHTopic;
    }
    public String getOtherHDesc() {
        return otherHDesc;
    }
    public String getOtherHImage() {
        return otherHImage;
    }


    public OHelpsDataClass(String otherHTopic, String otherHDesc, String otherHImage) {
        this.otherHTopic = otherHTopic;
        this.otherHDesc = otherHDesc;
        this.otherHImage = otherHImage;

    }

    public OHelpsDataClass(){

    }



}
