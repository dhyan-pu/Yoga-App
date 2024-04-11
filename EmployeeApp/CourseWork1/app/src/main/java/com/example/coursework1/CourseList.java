package com.example.coursework1;

public class CourseList {
    String typeOfClass, startAt, endAt, dayOfTheWeek, capacity, duration, pricePerClass, description;
    public String getTypeOfClass() {
        return typeOfClass;
    }

    public void setTypeOfClass(String typeOfClass) {
        this.typeOfClass = typeOfClass;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPricePerClass() {
        return pricePerClass;
    }

    public void setPricePerClass(String pricePerClass) {
        this.pricePerClass = pricePerClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseList(String typeOfClass, String startAt, String endAt, String dayOfTheWeek, String capacity, String duration, String pricePerClass, String description) {
        this.typeOfClass = typeOfClass;
        this.startAt = startAt;
        this.endAt = endAt;
        this.dayOfTheWeek = dayOfTheWeek;
        this.capacity = capacity;
        this.duration = duration;
        this.pricePerClass = pricePerClass;
        this.description = description;
    }
}
