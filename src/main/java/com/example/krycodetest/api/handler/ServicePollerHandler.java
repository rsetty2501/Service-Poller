package com.example.krycodetest.api.handler;

import com.example.krycodetest.config.DateTime;
import com.example.krycodetest.api.model.Service;
import com.example.krycodetest.api.repository.ServicePollerRepository;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ServicePollerHandler {

  private final ServicePollerRepository servicePollerRepository;
  private final HashMap<Integer, Service> services;

  public ServicePollerHandler(HashMap<Integer, Service> services){
    this.services=services;
    servicePollerRepository = new ServicePollerRepository();
  }

  public void getServices(RoutingContext routingContext) {

    // Get data from database
    Future<List<JsonArray>> listFuture = servicePollerRepository.getAll();

    // Send the data to requested service
    listFuture.onComplete(handler -> {

      List<JsonArray> data = listFuture.result();

      services.clear();

      for (JsonArray data1 : data) {
        Object id = data1.getInteger(0);
        Object name = data1.getString(1);
        Object url = data1.getString(2);
        Object updatedDateTime = data1.getString(3);
        Object statusResponse = data1.getString(4);

        // Store the data in Service object
        Service service = new Service(name.toString(), url.toString(), updatedDateTime.toString(), statusResponse.toString());
        services.put(Integer.valueOf(id.toString()), service);

      }

      List<JsonObject> jsonServices = services
        .entrySet()
        .stream()
        .map(service ->
          new JsonObject()
            .put("id", service.getKey())
            .put("name", service.getValue().getName())
            .put("url", service.getValue().getUrl())
            .put("dateTime", service.getValue().getUpdatedDateTime())
            .put("status", service.getValue().getStatusResponse()))
        .collect(Collectors.toList());

      routingContext.response()
        .putHeader("content-type", "application/json")
        .end(new JsonArray(jsonServices).encode());

    });

  }

  public void postService(RoutingContext routingContext) {

    JsonObject jsonObject = routingContext.getBodyAsJson();

    // Get the post request data
    String name = jsonObject.getString("serviceName");
    String url = jsonObject.getString("url");
    String statusResponse = "UNKNOWN"; // Initially status unknown

    // Get current date as String
    DateTime dateTime = new DateTime();
    String currDateTime = dateTime.getCurrDateTime();

    // Store all data in the service object
    Service service = new Service(name, url, currDateTime, statusResponse);

    servicePollerRepository.save(service);
    routingContext.response()
      .putHeader("content-type", "text/plain")
      .end("OK");

  }

  public void deleteService(RoutingContext routingContext) {

    JsonObject jsonObject = routingContext.getBodyAsJson();

    // Get id
    String idStr = jsonObject.getString("id");
    int id = Integer.parseInt(idStr);

    servicePollerRepository.delete(id);
    routingContext.response()
      .putHeader("content-type", "text/plain")
      .end("OK");

  }

}
