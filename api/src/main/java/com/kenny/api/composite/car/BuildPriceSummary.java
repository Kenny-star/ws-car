package com.kenny.api.composite.car;

public class BuildPriceSummary {
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

    public BuildPriceSummary(int buildPriceId, int seats, double cost, double rims, double bumper, String automatic, String color, String ledLights, String gpsEmbedded, String content) {
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
    }

    public BuildPriceSummary(){
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
    }
    public int getBuildPriceId() {
        return buildPriceId;
    }

    public void setBuildPriceId(int buildPriceId) {
        this.buildPriceId = buildPriceId;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRims() {
        return rims;
    }

    public void setRims(double rims) {
        this.rims = rims;
    }

    public double getBumper() {
        return bumper;
    }

    public void setBumper(double bumper) {
        this.bumper = bumper;
    }

    public String getAutomatic() {
        return automatic;
    }

    public void setAutomatic(String automatic) {
        this.automatic = automatic;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLedLights() {
        return ledLights;
    }

    public void setLedLights(String ledLights) {
        this.ledLights = ledLights;
    }

    public String getGpsEmbedded() {
        return gpsEmbedded;
    }

    public void setGpsEmbedded(String gpsEmbedded) {
        this.gpsEmbedded = gpsEmbedded;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
