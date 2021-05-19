package com.kenny.microservices.core.customer.GetCustomer.presentation.controllers;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotManufacturedException;
import com.kenny.api.core.customer.Customer;
import com.kenny.api.core.customer.customerGetAPI;
import com.kenny.microservices.core.customer.GetCustomer.businesslayer.GetCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCustomerRESTController implements customerGetAPI {

    private static final Logger Log = LoggerFactory.getLogger(GetCustomerRESTController.class);

    private final GetCustomerService customerService;

    public GetCustomerRESTController(GetCustomerService customerService){
        this.customerService = customerService;
    }

    @Override
    public List<Customer> getCustomer(int carId) {
        if (carId < 1) throw new InvalidInputException("Invalid carId: " + carId);
        if (carId == 5) throw new NotManufacturedException("Invalid carId: " + carId + ", they're no longer manufactured.");

        List<Customer> listCustomers = customerService.getByCarId(carId);
        /*
        if(carId == 213){
            Log.debug("No cars found for carId: {}", carId);
            return new ArrayList<>();
        }
        List<Customer> listCustomers= new ArrayList<>();
        listCustomers.add(new Customer(carId, 1, "John", "Doe", 56, "(514)-345-3245", "johnDoe@gmail.com", true, false, serviceUtil.getServiceAddress()));
        listCustomers.add(new Customer(carId, 2, "Robert", "Benjamin", 23, "(438)-123-5436", "robertBenjamin@hotmail.com", false, false, serviceUtil.getServiceAddress()));
        listCustomers.add(new Customer(carId, 3, "Dora", "Santiago", 32, "(231)-564-9986", "doraSantiago@gmail.com", false, true, serviceUtil.getServiceAddress()));
*/
        Log.debug("/cars found response size: {}", listCustomers.size());
        return listCustomers;
    }


}
