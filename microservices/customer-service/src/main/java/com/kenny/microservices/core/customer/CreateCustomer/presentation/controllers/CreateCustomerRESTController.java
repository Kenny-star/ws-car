package com.kenny.microservices.core.customer.CreateCustomer.presentation.controllers;

import com.kenny.api.core.customer.Customer;
import com.kenny.api.core.customer.customerCreateAPI;
import com.kenny.microservices.core.customer.CreateCustomer.businesslayer.CreateCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateCustomerRESTController implements customerCreateAPI {

    private static final Logger Log = LoggerFactory.getLogger(CreateCustomerRESTController.class);

    private final CreateCustomerService customerService;

    public CreateCustomerRESTController(CreateCustomerService customerService){
        this.customerService = customerService;
    }

    @Override
    public Customer createCustomer(Customer model) {
        Customer customer = customerService.createCustomer(model);
        Log.debug("REST Controller createCustomer: created an entity: {} / {}", customer.getCarId(), customer.getCustomerId());
        return customer;
    }

}
