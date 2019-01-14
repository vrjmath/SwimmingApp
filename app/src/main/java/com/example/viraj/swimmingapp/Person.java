package com.example.viraj.swimmingapp;

public class Person {

    // Store the id of the  movie poster

    // Store the name of the movie
    private String mName;
    // Store the release date of the movie
    private String mBestStroke;
    private String mAge;

    // Constructor that is used to create an instance of the Movie object
    public Person(String mName, String mBestStroke, String mAge) {
        this.mName = mName;
        this.mBestStroke = mBestStroke;
        this.mAge = mAge;
    }

    public String getmName() {
        return mName;
    }

    public String getmBestStroke() {
        if(mBestStroke == null) return "";
        return mBestStroke;
    }

    public void setmName(String mBestStroke) {
        this.mBestStroke= mBestStroke;
    }

    public String getmAge() {
        if(mAge == null) return "";
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }
}