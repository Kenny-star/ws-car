package com.kenny.microservices.core.customer.DeleteCustomer.presentation.controllers;

import com.kenny.api.core.customer.customerDeleteAPI;
import com.kenny.microservices.core.customer.DeleteCustomer.businesslayer.DeleteCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeleteCustomerRESTController implements customerDeleteAPI {

    private static final Logger Log = LoggerFactory.getLogger(DeleteCustomerRESTController.class);

    private final DeleteCustomerService customerService;

    public DeleteCustomerRESTController(DeleteCustomerService customerService){
        this.customerService = customerService;
    }


    @Override
    public void deleteCustomer(int carId) {
        Log.debug("REST Controller deleteCustomers: trying to delete customers for the car with carId: {}", carId);
        customerService.deleteCustomer(carId);
    }
}
