package com.kenny.microservices.core.carModel;

import com.kenny.api.core.carModel.CarModel;
import com.kenny.microservices.core.carModel.Dao.CarModelEntity;
import com.kenny.microservices.core.carModel.GetCarModel.datalayer.GetCarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"spring.data.mongodb.port: 0"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class CarModelServiceApplicationTests {

	private static final int CAR_ID_OKAY = 1;
	private static final int CAR_ID_NOT_FOUND = 13;
	private static final String CAR_ID_INVALID_STRING = "not-integer";
	private static final int CAR_ID_INVALID_NEGATIVE_VALUE = -1;
	private static final int CAR_NOT_MANUFACTURED = 5;

	@Autowired
	private WebTestClient client;

	@Autowired
	private GetCarModelRepository repository;

	@BeforeEach
	public void setupDb(){
		repository.deleteAll();
	}

	@Test
	public void getCarById(){

		CarModelEntity entity = new CarModelEntity(CAR_ID_OKAY, "name-" + CAR_ID_OKAY, "model-" + CAR_ID_OKAY, 1.0, "provider-" + CAR_ID_OKAY, 1, "country-" + CAR_ID_OKAY, "content-" + CAR_ID_OKAY);
		repository.save(entity);

		assertTrue(repository.findByCarId(CAR_ID_OKAY).isPresent());

		client.get()
				.uri("/carmodel/" + CAR_ID_OKAY)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.carId").isEqualTo(CAR_ID_OKAY);

	}

	@Test
	public void createCarModel(){

		//create an product model
		CarModel model = new CarModel(CAR_ID_OKAY, "name-" + CAR_ID_OKAY, "model-" + CAR_ID_OKAY, 1.0, "provider-1", 1, "country-1", "content-1", "SA");

		//sent the post request
		client.post()
				.uri("/carmodel")
				.body(just(model), CarModel.class)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.carId").isEqualTo(CAR_ID_OKAY);

		assertTrue(repository.findByCarId(CAR_ID_OKAY).isPresent());
	}

	@Test
	public void deleteCarModel(){
		CarModelEntity entity = new CarModelEntity(CAR_ID_OKAY, "name-" + CAR_ID_OKAY, "model-" + CAR_ID_OKAY, 1.0, "provider-" + CAR_ID_OKAY, 1, "country-" + CAR_ID_OKAY, "content-" + CAR_ID_OKAY);
		repository.save(entity);

		assertTrue(repository.findByCarId(CAR_ID_OKAY).isPresent());
		client.delete()
				.uri("/carmodel/" + CAR_ID_OKAY)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertFalse(repository.findByCarId(CAR_ID_OKAY).isPresent());
	}

	@Test
	public void getCarInvalidParameterString(){

		client.get()
				.uri("/carmodel/" + CAR_ID_INVALID_STRING)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/carmodel/" + CAR_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");

	}

	@Test
	public void getCarNotFound(){

		client.get()
				.uri("/carmodel/" + CAR_ID_NOT_FOUND)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/carmodel/" + CAR_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("No car found for carId: " + CAR_ID_NOT_FOUND);

	}

	@Test
	public void getCarNotManufacturedCar() {
		client.get()
				.uri("/carmodel/" + CAR_NOT_MANUFACTURED)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.GONE)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/carmodel/" + CAR_NOT_MANUFACTURED)
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_NOT_MANUFACTURED + ", they're no longer manufactured.");
	}

	@Test
	public void getCarInvalidParameterNegativeValue(){

		client.get()
				.uri("/carmodel/" + CAR_ID_INVALID_NEGATIVE_VALUE)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/carmodel/" + CAR_ID_INVALID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_ID_INVALID_NEGATIVE_VALUE);

	}

}
