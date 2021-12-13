package com.example.krycodetest.api.router;

import com.example.krycodetest.api.model.Service;
import com.example.krycodetest.api.handler.ServicePollerHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.HashMap;

public class ServicePollerRouter {

  private final Router router;
  private final ServicePollerHandler servicePollerHandler;

  public ServicePollerRouter(Router router, HashMap<Integer, Service> services){
    this.router = router;
    this.servicePollerHandler = new ServicePollerHandler(services);

  }

  public void setRoutes() {

    router.route("/*").handler(StaticHandler.create());

    router.get("/api/service").handler(servicePollerHandler::getServices);

    router.post("/api/service").handler(servicePollerHandler::postService);

    router.delete("/api/service").handler(servicePollerHandler::deleteService);

  }

}
