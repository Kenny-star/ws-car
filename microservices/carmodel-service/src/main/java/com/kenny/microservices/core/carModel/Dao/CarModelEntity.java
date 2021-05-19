package com.kenny.microservices.core.carModel.Dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carModels")
public class CarModelEntity {
    @Id
    private String id;

    @Version
    private Integer version;

    @Indexed(unique=true)
    private int carId;
    private String name;
    private String model;
    private Double cost;
    private String provider;
    private int release_year;
    private String country;
    private String content;

    public CarModelEntity() {
        carId = 0;
        name = null;
        model = null;
        cost = 0.0;
        provider = null;
        release_year = 0;
        country = null;
        content = null;
    }
    public CarModelEntity(int carId, String name, String model, Double cost, String provider, int release_year, String country, String content) {
        this.carId = carId;
        this.name = name;
        this.model = model;
        this.cost = cost;
        this.provider = provider;
        this.release_year = release_year;
        this.country = country;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}
