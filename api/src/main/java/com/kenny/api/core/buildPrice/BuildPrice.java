package com.kenny.api.core.buildPrice;

public class BuildPrice {
    private int carId;
    private int buildPriceId;
    private int seats;
    private double cost;
    private double rims;
    private double bumper;
    private String automatic;
    private String color;
    private String ledLights;
    private String gpsEmbedded;
    private String content;
    private String serviceAddress;


    public BuildPrice(){
        this.carId = 0;
        this.buildPriceId = 0;
        this.seats = 0;
        this.cost = 0.0;
        this.rims = 0.0;
        this.bumper = 0.0;
        this.automatic = null;
        this.color = null;
        this.ledLights = null;
        this.gpsEmbedded = null;
        this.content = null;
        this.serviceAddress = null;

    }

    public BuildPrice(int carId, int buildPriceId, int seats, double cost, double rims, double bumper, String automatic, String color, String ledLights, String gpsEmbedded, String content, String serviceAddress) {
        this.carId = carId;
        this.buildPriceId = buildPriceId;
        this.seats = seats;
        this.cost = cost;
        this.rims = rims;
        this.bumper = bumper;
        this.automatic = automatic;
        this.color = color;
        this.ledLights = ledLights;
        this.gpsEmbedded = gpsEmbedded;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public int getCarId() { return carId; }

    public int getBuildPriceId() {return buildPriceId; }

    public int getSeats() {
        return seats;
    }

    public double getCost() { return cost; }

    public Double getRims() {
        return rims;
    }

    public Double getBumper() {
        return bumper;
    }

    public String getAutomatic() {
        return automatic;
    }

    public String getColor() {
        return color;
    }

    public String getLedLights() {
        return ledLights;
    }

    public String getGpsEmbedded() {
        return gpsEmbedded;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getServiceAddress() { return serviceAddress; }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setBuildPriceId(int buildPriceId) {
        this.buildPriceId = buildPriceId;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setRims(Double rims) {
        this.rims = rims;
    }

    public void setBumper(Double bumper) {
        this.bumper = bumper;
    }

    public void setAutomatic(String automatic) {
        this.automatic = automatic;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLedLights(String ledLights) {
        this.ledLights = ledLights;
    }

    public void setGpsEmbedded(String gpsEmbedded) {
        this.gpsEmbedded = gpsEmbedded;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
