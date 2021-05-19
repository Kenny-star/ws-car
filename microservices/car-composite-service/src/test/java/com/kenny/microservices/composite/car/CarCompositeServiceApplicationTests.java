package com.kenny.microservices.composite.car;

import com.kenny.Utils.exceptions.InvalidInputException;
import com.kenny.Utils.exceptions.NotFoundException;
import com.kenny.api.composite.car.BuildPriceSummary;
import com.kenny.api.composite.car.CarAggregate;
import com.kenny.api.composite.car.CustomerSummary;
import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.api.core.carModel.CarModel;
import com.kenny.api.core.customer.Customer;
import com.kenny.microservices.composite.car.getCar.integrationlayer.CarCompositeGetIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static reactor.core.publisher.Mono.just;

@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT )
class CarCompositeServiceApplicationTests {

	private static final int CAR_ID_OKAY = 1;
	private static final int CAR_ID_NOT_FOUND = 113;
	private static final String CAR_ID_INVALID_STRING = "not-integer";
	private static final int CAR_ID_NEGATIVE_VALUE = -1;
	private static final int CAR_NOT_MANUFACTURED = 5;


	@Autowired
	private WebTestClient client;

	@MockBean
	private CarCompositeGetIntegration getCompositeIntegration;


	@BeforeEach
	void setup(){

		when(getCompositeIntegration.getCarModel(CAR_ID_OKAY))
				.thenReturn(new CarModel(CAR_ID_OKAY, "name-1", "model-1", 100000.0, "provider-1", 2001, "country-1", "content-1", "mock address"));

		//BDD equivalent
		given(getCompositeIntegration.getCarModel(CAR_ID_OKAY))
				.willReturn(new CarModel(CAR_ID_OKAY, "name-1", "model-1", 100000.0, "provider-1", 2001, "country-1", "content-1", "mock address"));

		when(getCompositeIntegration.getBuildPrice(CAR_ID_OKAY))
				.thenReturn(singletonList(new BuildPrice(CAR_ID_OKAY, 1 , 1, 1.0, 1.0, 1.0, "automatic-1", "color-1", "ledLights-1", "gpsEmbedded-1","content-1", "mock address")));

		when(getCompositeIntegration.getCustomer(CAR_ID_OKAY))
				.thenReturn(singletonList(new Customer(CAR_ID_OKAY, 1, "firstName-1", "lastName-1", 10, "phoneNumber-1", "email-1", true, true, "content-1","mock address")));


		when(getCompositeIntegration.getCarModel(CAR_ID_NOT_FOUND))
				.thenThrow(new NotFoundException("NOT FOUND: "+ CAR_ID_NOT_FOUND));

		when(getCompositeIntegration.getCarModel(CAR_ID_NEGATIVE_VALUE))
				.thenThrow(new InvalidInputException("INVALID: "+ CAR_ID_NEGATIVE_VALUE));

	}

	@Test
	public void getCarById(){

		int expectedLength = 1;

		client.get()
				.uri("/car-composite/" + CAR_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.carId").isEqualTo(CAR_ID_OKAY)
				.jsonPath("$.buildPrice.length()").isEqualTo(expectedLength)
				.jsonPath("$.customer.length()").isEqualTo(expectedLength);

	}

	@Test
	public void createCompositeCarNoBuildPricesNoCustomers(){
		CarAggregate compositeCar = new CarAggregate(CAR_ID_OKAY, "name-1", "model-1", 100000.0, "provider-1", 2001, "country-1", "content-1", null, null, null);

		client.post()
				.uri("/car-composite")
				.body(just(compositeCar), CarAggregate.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void getCarNotFound(){

		client.get()
				.uri("/car-composite/" + CAR_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/car-composite/" + CAR_ID_NOT_FOUND)
				.jsonPath("$.message").isEqualTo("NOT FOUND: "+ CAR_ID_NOT_FOUND);

	}

	@Test
	public void createCompositeCarOneBuildPriceOneCustomer(){
		CarAggregate compositeCar = new CarAggregate(CAR_ID_OKAY, "name-1", "model-1", 100000.0, "provider-1", 2001, "country-1", "content-1",
				singletonList(new BuildPriceSummary(1, 1, 100000.0, 100.0, 100.0, "automatic-1", "color-1", "ledLights-1", "gpsEmbedded-1", "content-1")),
				singletonList(new CustomerSummary(1, "fn", "ln", 40, "pn", "ea", false, false, "c")), null);

		client.post()
				.uri("/car-composite")
				.body(just(compositeCar), CarAggregate.class)
				.exchange()
				.expectStatus().isOk();

	}


	@Test
	public void deleteCompositeCar(){
		CarAggregate compositeCar = new CarAggregate(CAR_ID_OKAY, "name-1", "model-1", 100000.0, "provider-1", 2001, "country-1", "content-1",
				singletonList(new BuildPriceSummary(1, 1, 100000.0, 100.0, 100.0, "automatic-1", "color-1", "ledLights-1", "gpsEmbedded-1", "content-1")),
				singletonList(new CustomerSummary(1, "fn", "ln", 40, "pn", "ea", false, false, "c")), null);

		client.post()
				.uri("/car-composite")
				.body(just(compositeCar), CarAggregate.class)
				.exchange()
				.expectStatus().isOk();

		client.delete()
				.uri("/car-composite/" + compositeCar.getCarId())
				.exchange()
				.expectStatus().isOk();

		client.delete()
				.uri("/car-composite/" + compositeCar.getCarId())
				.exchange()
				.expectStatus().isOk();
	}


	@Test
	public void getCarInvalidParameterNegativeValue(){
		client.get()
				.uri("/car-composite/" + CAR_ID_NEGATIVE_VALUE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/car-composite/" + CAR_ID_NEGATIVE_VALUE)
				.jsonPath("$.message").isEqualTo("INVALID: "+ CAR_ID_NEGATIVE_VALUE);
	}

	@Test
	public void getCarInvalidParameterStringValue(){
		client.get()
				.uri("/car-composite/" + CAR_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/car-composite/" + CAR_ID_INVALID_STRING)
				.jsonPath("$.message").isEqualTo("Type mismatch.");
	}

}
