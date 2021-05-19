package com.kenny.api.composite.car;

import org.springframework.web.bind.annotation.*;

public interface GetCarCompositeServiceAPI {
    @GetMapping(
            value = "/car-composite/{carId}",
            produces = "application/json")
    CarAggregate getCompositeCar(@PathVariable int carId);

}
