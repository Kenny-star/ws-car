package com.kenny.microservices.core.customer.CreateCustomer.datalayer;

import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CreateCustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    @Transactional(readOnly = true)
    List<CustomerEntity> findByCarId(int carId);
}