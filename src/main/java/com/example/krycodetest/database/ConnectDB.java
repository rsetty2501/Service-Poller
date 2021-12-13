package com.example.krycodetest.database;

import com.example.krycodetest.config.DbConfig;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

public class ConnectDB {

  private final JDBCClient jdbcClient;

  public ConnectDB(Vertx vertx){

    JsonObject config = new DbConfig().getJDBCConfig();
    jdbcClient = JDBCClient.createShared(vertx,config);

  }

  public JDBCClient connectToDb(){

    return jdbcClient;

  }


}
