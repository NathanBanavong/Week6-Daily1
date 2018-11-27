package com.example.consultants.week6_daily1;

public class RestaurantInfo {
    String name;
    String address;
    String foodtype;

    public RestaurantInfo(String name, String address, String foodtype) {
        this.name = name;
        this.address = address;
        this.foodtype = foodtype;
    }

    @Override
    public String toString() {
        return "RestaurantInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", foodtype='" + foodtype + '\'' +
                '}';
    }
}
