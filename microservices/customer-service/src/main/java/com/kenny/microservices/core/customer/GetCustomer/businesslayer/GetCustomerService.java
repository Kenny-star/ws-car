package com.kenny.microservices.core.customer.GetCustomer.businesslayer;

import com.kenny.api.core.customer.Customer;

import java.util.List;

public interface GetCustomerService {
    public List<Customer> getByCarId(int carId);
}
