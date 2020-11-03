package com.hamzeh.moraghebehapp.data.pojo;

import java.util.HashMap;

public class RatingSubjectPojo {

    private int position; //This is important, so we'll pass it to the constructor.
    private int amalSize;
    private int arbayiinId;
    private String rating;
    private String arbTitle;
    private int viewType;
    private HashMap<Integer, String> resultsHashMap;

    public RatingSubjectPojo() {
    }

    public static class Builder {
        private int position; //This is important, so we'll pass it to the constructor.
        private int amalSize;
        private int arbayiinId;
        private String rating;
        private String arbTitle;
        private int viewType;
        private HashMap<Integer, String> resultsHashMap;


        public Builder(int position) {
            this.position = position;
        }
        public Builder setRating(String rating){
            this.rating = rating;
            return this;  //By returning the builder each time, we can create a fluent interface.
        }
        public Builder setAmalSize(int amalSize){
            this.amalSize = amalSize;
            return this;
        }
        public Builder setArbayiinId(int arbayiinId){
            this.arbayiinId = arbayiinId;
            return this;
        }

        public Builder setArbayiinTitle(String arbayiinTitle){
            this.arbTitle = arbayiinTitle;
            return this;
        }

        public Builder setViewType(int viewType){
            this.viewType = viewType;
            return this;
        }

        public Builder setResultsHashMap(HashMap<Integer, String> resultsHashMap){
            this.resultsHashMap = resultsHashMap;
            return this;
        }



        public RatingSubjectPojo build(){
            //Here we create the actual bank ratingSubjectPojo object, which is always in a fully initialised state when it's returned.
            RatingSubjectPojo ratingSubjectPojo = new RatingSubjectPojo();  //Since the builder is in the RatingSubjectPojo class, we can invoke its private constructor.
            ratingSubjectPojo.position = this.position;
            ratingSubjectPojo.rating = this.rating;
            ratingSubjectPojo.amalSize = this.amalSize;
            ratingSubjectPojo.arbayiinId = this.arbayiinId;
            ratingSubjectPojo.arbTitle = this.arbTitle;
            ratingSubjectPojo.viewType = this.viewType;
            return ratingSubjectPojo;
        }
    }
    //Fields omitted for brevity.

    public int getPosition() {
        return position;
    }

    public int getAmalSize() {
        return amalSize;
    }

    public int getArbayiinId() {
        return arbayiinId;
    }

    public String getRating() {
        return rating;
    }

    public String getArbTitle() {
        return arbTitle;
    }

    public int getViewType() {
        return viewType;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    //Getters and setters omitted for brevity.
}