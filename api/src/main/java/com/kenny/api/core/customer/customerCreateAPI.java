package com.kenny.api.core.customer;


import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface customerCreateAPI {

    @PostMapping(
            value = "/customer",
            consumes = "application/json",
            produces = "application/json"
    )
    Customer createCustomer(@RequestBody Customer model);

}
