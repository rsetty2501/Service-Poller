package com.example.krycodetest.config;

import io.vertx.core.json.JsonObject;

public class DbConfig {

  private static final String DATABASE_PATH = "service_poller.db";

  public DbConfig(){

  }

  public JsonObject getJDBCConfig(){
    return new JsonObject()
      .put("url", "jdbc:sqlite:" + DATABASE_PATH)
      .put("driver_class", "org.sqlite.JDBC")
      .put("max_pool_size", 50);

  }

}
