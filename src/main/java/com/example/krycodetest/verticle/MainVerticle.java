package com.example.krycodetest.verticle;

import com.example.krycodetest.api.router.ServicePollerRouter;
import com.example.krycodetest.database.ConnectDB;
import com.example.krycodetest.task.PeriodicServicePoller;
import com.example.krycodetest.api.model.Service;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import java.util.HashMap;

public class MainVerticle extends AbstractVerticle {

  private final HashMap<Integer, Service> services = new HashMap<>();

  @Override
  public void start(Promise<Void> startPromise) {

    // Connect to the database
    new ConnectDB(vertx);

    // Route handling
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create()); // Gather entire request body
    ServicePollerRouter routeHandler = new ServicePollerRouter(router, services);
    routeHandler.setRoutes();

    // Periodic service poller
    PeriodicServicePoller poller = new PeriodicServicePoller(vertx, services);
    poller.setPeriodicPoller();

    // Create HttpServer
    vertx
      .createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
      }
    });


  }

}
