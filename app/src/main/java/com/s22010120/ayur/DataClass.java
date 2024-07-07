package com.s22010120.ayur;

public class DataClass {

    private String dataTopic;
    private String dataDesc;
    private String dataImage;


    private String dataDate;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTopic() {
        return dataTopic;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataImage() {
        return dataImage;
    }
    public String getDataDate() {
        return dataDate;
    }



    public DataClass(String dataTopic, String dataDesc,String dataDate, String dataImage) {
        this.dataTopic = dataTopic;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
        this.dataDate = dataDate;
    }

    public DataClass(){

    }
}
