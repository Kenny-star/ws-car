package com.kenny.microservices.core.buildPrice;

import com.kenny.api.core.buildPrice.BuildPrice;
import com.kenny.microservices.core.buildPrice.Dao.BuildPriceEntity;
import com.kenny.microservices.core.buildPrice.GetBuildPrice.datalayer.GetBuildPriceRepository;
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

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = { "spring.datasource.url=jdbc:h2:mem:buildprice-db"})
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
class BuildPriceServiceApplicationTests {

	private static final int CAR_ID_OKAY = 1;
	private static final int CAR_ID_NOT_FOUND = 113;
	private static final String CAR_ID_INVALID_STRING = "not-integer";
	private static final int CAR_ID_INVALID_NEGATIVE = -1;
	private static final int CAR_NOT_MANUFACTURED = 5;
	private static final int BUILDPRICE_ID = 1;

	@Autowired
	private WebTestClient client;

	@Autowired
	private GetBuildPriceRepository repository;

	@BeforeEach
	public void setupDb(){
		repository.deleteAll();
	}

	@Test
	public void getBuildPriceByCarId(){
		int expectedLength = 3;

		BuildPriceEntity entity1 = new BuildPriceEntity(CAR_ID_OKAY, BUILDPRICE_ID, BUILDPRICE_ID, 1.0, 2.0, 3.0, "automatic-1", "color-1", "ledLights-1", "gpsEmbedded-1","content-1");
		repository.save(entity1);

		BuildPriceEntity entity2 = new BuildPriceEntity(CAR_ID_OKAY, BUILDPRICE_ID + 1, BUILDPRICE_ID + 1, 1.0, 2.0, 3.0, "automatic-2", "color-2", "ledLights-2", "gpsEmbedded-2","content-2");
		repository.save(entity2);

		BuildPriceEntity entity3 = new BuildPriceEntity(CAR_ID_OKAY, BUILDPRICE_ID + 2, BUILDPRICE_ID + 2, 1.0, 2.0, 3.0, "automatic-3", "color-3", "ledLights-3", "gpsEmbedded-3","content-3");
		repository.save(entity3);

		assertEquals(expectedLength, repository.findByCarId(CAR_ID_OKAY).size());

		client.get()
				.uri("/buildprice?carId=" + CAR_ID_OKAY)
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
	public void createBuildPrice(){

		int expectedSize = 1;
		BuildPrice recommendation = new BuildPrice(CAR_ID_OKAY, BUILDPRICE_ID, BUILDPRICE_ID,1.0, 1.0, 1.0, "automatic-" + BUILDPRICE_ID, "color-" + BUILDPRICE_ID, "ledLights-" + BUILDPRICE_ID, "gpsEmbedded-" + BUILDPRICE_ID, "Content " + BUILDPRICE_ID, "SA");

		client.post()
				.uri("/buildprice")
				.body(just(recommendation),BuildPrice.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody();

		assertEquals(expectedSize, repository.findByCarId(CAR_ID_OKAY).size());
	}

	@Test
	public void deleteBuildPrices() {

		BuildPriceEntity entity = new BuildPriceEntity(CAR_ID_OKAY, BUILDPRICE_ID, BUILDPRICE_ID, 1.0, 2.0, 3.0, "automatic-1", "color-1", "ledLights-1", "gpsEmbedded-1","content-1");
		repository.save(entity);

		assertEquals(1,repository.findByCarId(CAR_ID_OKAY).size());

		client.delete()
				.uri("/buildprice?carId=" + CAR_ID_OKAY)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody();

		assertEquals(0, repository.findByCarId(CAR_ID_OKAY).size());
	}

	@Test
	public void getBuildPriceMissingParameter(){

		client.get()
				.uri("/buildprice")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/buildprice")
				.jsonPath("$.message").isEqualTo("Required int parameter 'carId' is not present" );
	}

	@Test
	public void getBuildPriceInvalidParameterString(){

		client.get()
				.uri("/buildprice?carId=" + CAR_ID_INVALID_STRING)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isBadRequest()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/buildprice")
				.jsonPath("$.message").isEqualTo("Type mismatch." );
	}

	@Test
	public void getBuildPriceInvalidParameterNegativeValue(){

		client.get()
				.uri("/buildprice?carId=" + CAR_ID_INVALID_NEGATIVE)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/buildprice")
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_ID_INVALID_NEGATIVE );
	}
	@Test
	public void getBuildPriceNotManufacturedCar() {
		client.get()
				.uri("/buildprice?carId=" + CAR_NOT_MANUFACTURED)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isEqualTo(HttpStatus.GONE)
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/buildprice")
				.jsonPath("$.message").isEqualTo("Invalid carId: " + CAR_NOT_MANUFACTURED + ", they're no longer manufactured.");
	}
	@Test
	public void getBuildPriceNotFound(){

		int expectedLength = 0;

		client.get()
				.uri("/buildprice?carId=" + CAR_ID_NOT_FOUND)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.length()").isEqualTo(expectedLength);
	}

}
