package com.kenny.microservices.core.buildPrice.Dao;

import javax.persistence.*;

@Entity
@Table(name = "buildprices", indexes = { @Index(name = "buildprices_unique_idx", unique = true, columnList = "carId,buildPriceId") })
public class BuildPriceEntity {

    @Id
    @GeneratedValue
    private int id;

    @Version
    private int version;

    private int carId;
    private int buildPriceId;
    private int seats;
    private double cost;
    private double rims;
    private double bumper;
    private String automatic;
    private String coloration;
    private String ledLights;
    private String gpsEmbedded;
    private String content;

    public BuildPriceEntity() {
        this.carId = 0;
        this.buildPriceId = 0;
        this.seats = 0;
        this.cost = 0.0;
        this.rims = 0.0;
        this.bumper = 0.0;
        this.automatic = null;
        this.coloration = null;
        this.ledLights = null;
        this.gpsEmbedded = null;
        this.content = null;
    }
    public BuildPriceEntity( int carId, int buildPriceId, int seats, double cost, double rims, double bumper, String automatic, String coloration, String ledLights, String gpsEmbedded, String content) {

        this.carId = carId;
        this.buildPriceId = buildPriceId;
        this.seats = seats;
        this.cost = cost;
        this.rims = rims;
        this.bumper = bumper;
        this.automatic = automatic;
        this.coloration = coloration;
        this.ledLights = ledLights;
        this.gpsEmbedded = gpsEmbedded;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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

    public String getColoration() {
        return coloration;
    }

    public void setColoration(String coloration) {
        this.coloration = coloration;
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
