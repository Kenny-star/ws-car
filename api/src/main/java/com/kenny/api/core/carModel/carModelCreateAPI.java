package com.kenny.api.core.carModel;

import org.springframework.web.bind.annotation.*;

public interface carModelCreateAPI {

    @PostMapping(
            value = "/carmodel",
            consumes = "application/json",
            produces = "application/json"
    )

    CarModel createCarModel(@RequestBody CarModel model);

}
