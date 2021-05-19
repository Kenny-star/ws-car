package com.kenny.api.core.customer;


import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface customerGetAPI {

    @GetMapping(
            value = "/customer",
            produces = "application/json"
    )
    List<Customer> getCustomer(@RequestParam(value = "carId", required = true) int carId);

}
