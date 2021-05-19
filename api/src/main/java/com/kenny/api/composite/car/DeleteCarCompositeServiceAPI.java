package com.kenny.api.composite.car;

import org.springframework.web.bind.annotation.*;

public interface DeleteCarCompositeServiceAPI {

    @DeleteMapping(value = "/car-composite/{carId}")
    void deleteCompositeCar(@PathVariable int carId);
}
