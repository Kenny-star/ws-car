package com.kenny.microservices.core.customer.DeleteCustomer.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.microservices.core.customer.DeleteCustomer.datalayer.DeleteCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteCustomerServiceImpl implements DeleteCustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteCustomerServiceImpl.class);

    private final DeleteCustomerRepository repository;

    private final DeleteCustomerMapper mapper;

    private final ServiceUtil serviceUtil;

    public DeleteCustomerServiceImpl(DeleteCustomerRepository repository, DeleteCustomerMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }


    @Override
    public void deleteCustomer(int carId) {
        LOG.debug("CustomerService deleteCustomers: tries to delete all customers for the car with carId: {}", carId);
        repository.deleteAll(repository.findByCarId(carId));
    }
}
