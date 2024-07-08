package com.s22010120.ayur;

public class AthBehethDataClass {

    private String athBehethTopic;
    private String athBehethDesc;
    private String athBehethImage;
    private String abDataDate;
    private String abKey;

    public String getAbKey() {
        return abKey;
    }

    public void setAbKey(String abKey) {
        this.abKey = abKey;
    }

    public String getAthBehethTopic() {
        return athBehethTopic;
    }

    public String getAthBehethDesc() {
        return athBehethDesc;
    }

    public String getAthBehethImage() {
        return athBehethImage;
    }
    public String getAbDataDate(){
        return  abDataDate;
    }

    public AthBehethDataClass(String athBehethTopic, String athBehethDesc,String abDataDate, String athBehethImage) {
        this.athBehethTopic = athBehethTopic;
        this.athBehethDesc = athBehethDesc;
        this.abDataDate = abDataDate;
        this.athBehethImage = athBehethImage;
    }

    public AthBehethDataClass(){

    }




}
