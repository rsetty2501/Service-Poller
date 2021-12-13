package com.example.krycodetest.api;

import com.example.krycodetest.verticle.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(VertxExtension.class)
public class ComponentTest{

  @BeforeAll
  static void setup(Vertx vertx,
                    VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(migrationVerticleId ->
        testContext.completeNow()));
  }

  @Test
  @Order(1)
  @DisplayName("Read all services")
  void readAll(Vertx vertx, VertxTestContext testContext) {
    final WebClient webClient = WebClient.create(vertx);

    webClient.get(8080, "localhost", "/api/service")
      .as(BodyCodec.jsonArray())
      .send(testContext.succeeding(response -> {
          testContext.verify(() ->
            Assertions.assertAll(
              () -> Assertions.assertEquals(200, response.statusCode())
            )
          );

          testContext.completeNow();
        })
      );
  }


}
