package com.kenny.microservices.core.customer.DeleteCustomer.datalayer;

import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeleteCustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    @Transactional(readOnly = true)
    List<CustomerEntity> findByCarId(int carId);
}