package com.example.zedcrest.medmanager;

/**
 * Created by ZEDCREST on 12/04/2018.
 */

public class Medication {
    String  id;
    String  name;
    String  description;
    String interval;
    String start_date;
    String end_date;
    String category;

    public  Medication(){

    }

    public Medication(String id, String name, String category,String description,String interval,String start_date,String end_date){
        this.id = id;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.start_date = start_date;
        this.end_date = end_date;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getInterval() {
        return interval;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getId() {
        return id;
    }
}
