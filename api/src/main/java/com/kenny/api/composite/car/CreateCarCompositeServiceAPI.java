package com.kenny.api.composite.car;

import org.springframework.web.bind.annotation.*;

public interface CreateCarCompositeServiceAPI {

    @PostMapping(
            value = "/car-composite",
            consumes = "application/json"
    )

    void createCompositeCar(@RequestBody CarAggregate model);


}
