package com.kenny.microservices.core.customer;

import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import com.kenny.microservices.core.customer.GetCustomer.datalayer.GetCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
public class PersistenceTests {

    @Autowired
    private GetCustomerRepository repository;

    private CustomerEntity savedEntity;

    @BeforeEach
    public void setupDb() {
        repository.deleteAll();

        CustomerEntity entity = new CustomerEntity(1, 2, "fn", "ln", 3, "pn", "ea", false, false, "dc");
        savedEntity = repository.save(entity);

        assertThat(savedEntity, samePropertyValuesAs(entity));
    }

    @Test
    public void create() {

        CustomerEntity newEntity = new CustomerEntity(1, 3, "fn", "ln", 4, "pn", "ea", false, false, "dc");
        repository.save(newEntity);

        CustomerEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertThat(foundEntity, samePropertyValuesAs(newEntity));

        assertEquals(2, repository.count());
    }

    @Test
    public void update() {
        savedEntity.setFirstName("fn-1");
        repository.save(savedEntity);

        CustomerEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (long)foundEntity.getVersion());
        assertEquals("fn-1", foundEntity.getFirstName());
    }

    @Test
    public void delete() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    public void getByCarId() {
        List<CustomerEntity> entityList = repository.findByCarId(savedEntity.getCarId());

        assertThat(entityList, hasSize(1));

        assertThat(entityList.get(0), samePropertyValuesAs(savedEntity));
    }
}