package com.kenny.microservices.core.customer.DeleteCustomer.businesslayer;

import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeleteCustomerMapper {
    @Mapping(target = "serviceAddress", ignore = true)
    Customer entityToModel(CustomerEntity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "version", ignore = true)
    })

    CustomerEntity modelToEntity(Customer model);

    List<Customer> entityListToModelList(List<CustomerEntity> entity);

    List<Customer> modelListToEntityList(List<CustomerEntity> model);
}
