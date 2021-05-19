package com.kenny.api.core.carModel;

public class CarModel {
    private int carId;
    private String name;
    private String model;
    private Double cost;
    private String provider;
    private int release_year;
    private String country;
    private String content;
    private String serviceAddress;

    public CarModel(){
        carId = 0;
        name = null;
        model = null;
        cost = 0.0;
        provider = null;
        release_year = 0;
        country = null;
        content = null;
        serviceAddress = null;
    }
    public CarModel(int carId, String name, String model, Double cost, String provider, int release_year, String country, String content, String serviceAddress) {
        this.carId = carId;
        this.name = name;
        this.model = model;
        this.cost = cost;
        this.provider = provider;
        this.release_year = release_year;
        this.country = country;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
