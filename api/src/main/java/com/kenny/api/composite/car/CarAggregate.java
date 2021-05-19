package com.kenny.api.composite.car;

import java.util.List;

public class CarAggregate {
    private int carId;
    private String name;
    private String model;
    private Double cost;
    private String provider;
    private int release_year;
    private String country;
    private String content;
    private List<BuildPriceSummary> buildPrice;
    private List<CustomerSummary> customer;
    private ServiceAddress serviceAddress;


    public CarAggregate(int carId, String name, String model, Double cost, String provider, int release_year, String country, String content, List<BuildPriceSummary> buildPrice, List<CustomerSummary> customer, ServiceAddress serviceAddress) {
        this.carId = carId;
        this.name = name;
        this.model = model;
        this.cost = cost;
        this.provider = provider;
        this.release_year = release_year;
        this.country = country;
        this.content = content;
        this.buildPrice = buildPrice;
        this.customer = customer;
        this.serviceAddress = serviceAddress;
    }

    public CarAggregate(){
        this.carId = 0;
        this.name = null;
        this.model = null;
        this.cost = 0.0;
        this.provider = null;
        this.release_year = 0;
        this.country = null;
        this.content = null;
        this.buildPrice = null;
        this.customer = null;
        this.serviceAddress = null;
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

    public List<BuildPriceSummary> getBuildPrice() { return buildPrice; }

    public void setBuildPrices(List<BuildPriceSummary> buildPrice) {
        this.buildPrice = buildPrice;
    }

    public List<CustomerSummary> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerSummary> customer) {
        this.customer = customer;
    }

    public ServiceAddress getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(ServiceAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
