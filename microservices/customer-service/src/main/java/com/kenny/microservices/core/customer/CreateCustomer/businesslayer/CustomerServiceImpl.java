package com.kenny.microservices.core.customer.CreateCustomer.businesslayer;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.core.customer.CreateCustomer.datalayer.CreateCustomerRepository;
import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CreateCustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CreateCustomerRepository repository;

    private final CreateCustomerMapper mapper;

    private final ServiceUtil serviceUtil;

    public CustomerServiceImpl(CreateCustomerRepository repository, CreateCustomerMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Customer createCustomer(Customer model) {
        try{
            CustomerEntity entity = mapper.modelToEntity(model);
            CustomerEntity newEntity = repository.save(entity);
            LOG.debug("createCustomer: entity created for carId: {}", model.getCarId());
            return mapper.entityToModel(newEntity);
        }catch (DuplicateKeyException dke){
            throw new InvalidInputException("Duplicate key for carId: " + model.getCarId());
        }
    }

}
