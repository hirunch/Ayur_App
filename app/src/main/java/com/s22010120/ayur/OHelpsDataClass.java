package com.s22010120.ayur;

public class OHelpsDataClass {
    private String otherHImage;
    private String otherHTopic;
    private String otherHDesc;
    private String ohDateData;
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
    public String getOhDateData(){
        return ohDateData;
    }

    // Constructor for OHelpsDataClass that initializes its fields with provided values
    public OHelpsDataClass(String otherHTopic, String otherHDesc, String ohDateData, String otherHImage) {
        this.otherHTopic = otherHTopic;
        this.otherHDesc = otherHDesc;
        this.otherHImage = otherHImage;
        this.ohDateData = ohDateData;

    }

    public OHelpsDataClass(){

    }



}
