package com.kenny.api.core.customer;


import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface customerDeleteAPI {

    @DeleteMapping(
            value = "/customer"
    )
    void deleteCustomer(@RequestParam(value = "carId", required = true) int carId);
}
