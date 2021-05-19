package com.kenny.microservices.core.customer.GetCustomer.businesslayer;

import com.kenny.Utils.http.ServiceUtil;
import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import com.kenny.microservices.core.customer.GetCustomer.datalayer.GetCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCustomerServiceImpl implements GetCustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(GetCustomerServiceImpl.class);

    private final GetCustomerRepository repository;

    private final GetCustomerMapper mapper;

    private final ServiceUtil serviceUtil;

    public GetCustomerServiceImpl(GetCustomerRepository repository, GetCustomerMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public List<Customer> getByCarId(int carId) {

        List<CustomerEntity> entityList = repository.findByCarId((carId));
        List<Customer> list = mapper.entityListToModelList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

        LOG.debug("Reviews getByCarId: response size: {}", list.size());
        return list;
    }

}
