package com.kenny.microservices.core.customer.CreateCustomer.businesslayer;

import com.kenny.api.core.customer.Customer;

import java.util.List;

public interface CreateCustomerService {
    public Customer createCustomer(Customer model);
}
