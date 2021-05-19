package com.kenny.microservices.core.customer;

import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.core.customer.Dao.CustomerEntity;
import com.kenny.microservices.core.customer.GetCustomer.datalayer.GetCustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = { "spring.datasource.url=jdbc:h2:mem:customer-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CustomerServiceApplicationTests {

	private static final int CAR_ID_OKAY = 1;
	private static final int CAR_ID_NOT_FOUND = 213;
	private static final String CAR_ID_INVALID_STRING = "not-integer";
	private static final int CAR_ID_INVALID_NEGATIVE = -1;
	private static final int CAR_NOT_MANUFACTURED = 5;

	private static final int CUSTOMER_ID = 1;

	@Autowired
	private WebTestClient client;

	@Autowired
	GetCustomerRepository repository;

	@BeforeEach
	public void setupDb(){repository.deleteAll();}

	@Test
	public void getCustomersByCarId(){
		int expectedLength = 3;

		//add the reviews to the repo
		CustomerEntity entity1 = new CustomerEntity(CAR_ID_OKAY, 1, "firstName-1", "lastName-1", 10, "phoneNumber-1", "email-1", false, true, "description-1");
		repository.save(entity1);
		CustomerEntity entity2 = new CustomerEntity(CAR_ID_OKAY, 2, "firstName-2", "lastName-2", 20, "phoneNumber-2", "email-2", true, true, "description-2");
		repository.save(entity2);
		CustomerEntity entity3 = new CustomerEntity(CAR_ID_OKAY, 3, "firstName-3", "lastName-3", 30, "phoneNumber-3", "email-3", true, false, "description-3");
		repository.save(entity3);

		assertEquals(expectedLength, repository.findByCarId(CAR_ID_OKAY).size());

		client.get()
				.uri("/customer?carId=" + CAR_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength)
				.jsonPath("$[0].carId").isEqualTo(CAR_ID_OKAY)
				.jsonPath("$[1].carId").isEqualTo(CAR_ID_OKAY)
				.jsonPath("$[2].carId").isEqualTo(CAR_ID_OKAY);
	}

	@Test
	public void createCustomer(){
		int expectedSize =1;
		//create the review

		Customer customer = new Customer(CAR_ID_OKAY, CUSTOMER_ID,
				"FirstName " + CUSTOMER_ID, "LastName" + CUSTOMER_ID, 10, "PhoneNumber-" + CUSTOMER_ID, "Email-" + CUSTOMER_ID, false, false, "Content-" + CUSTOMER_ID, "SA");

		//send the POST request
		client.post()
				.uri("/customer")
				.body(just(customer), Customer.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByCarId(CAR_ID_OKAY).size());


	}

	@Test
	void deleteCustomers(){

		//create a review entity

		CustomerEntity entity = new CustomerEntity(CAR_ID_OKAY, CUSTOMER_ID, "FirstName " + CUSTOMER_ID, "LastName" + CUSTOMER_ID, 10, "PhoneNumber-" + CUSTOMER_ID, "Email-" + CUSTOMER_ID, false, false, "SA");
		//save it
		repository.save(entity);

		//verify there are exactly 1  entity for product id 1
		assertEquals(1, repository.findByCarId(CAR_ID_OKAY).size());

		//send the DELETE request
		client.delete()
				.uri("/customer?carId=" + CAR_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody();

		//verify there are no entities for product id 1
		assertEquals(0, repository.findByCarId(CAR_ID_OKAY).size());
	}

	@Test
	public void getCustomersMissingParameter(){

		client.get()
				.uri("/customer")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/customer")
				.jsonPath("$.message").isEqualTo("Required int parameter 'carId' is not present" );
	}

	@Test
	public void getCustomersInvalidParameterString(){

		client.get()
				.uri("/customer?carId=" + CAR_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/customer")
				.jsonPath("$.message").isEqualTo("Type mismatch." );
	}

	@Test
	public void getCustomersInvalidParameterNegativeValue(){

		client.get()
				.uri("/customer?carId=" + CAR_ID_INVALID_NEGATIVE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/customer")
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_ID_INVALID_NEGATIVE );
	}

	@Test
	public void getCustomersNotManufacturedCar(){

		client.get()
				.uri("/customer?carId=" + CAR_NOT_MANUFACTURED)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.GONE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/customer")
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_NOT_MANUFACTURED + ", they're no longer manufactured.");
	}

	@Test
	public void getCustomersNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/customer?carId=" + CAR_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

}
