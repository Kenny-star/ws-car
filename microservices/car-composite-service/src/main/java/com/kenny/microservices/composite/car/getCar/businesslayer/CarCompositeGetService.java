package com.kenny.microservices.composite.car.getCar.businesslayer;

import com.kenny.api.composite.car.CarAggregate;

public interface CarCompositeGetService {
    public CarAggregate getCarModel(int carId);
}
