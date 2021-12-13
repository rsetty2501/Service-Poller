package com.example.krycodetest.database;

import com.example.krycodetest.utils.DbUtils;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.sql.SQLConnection;

public class CreateTable {

  private final Vertx vertx;

  public CreateTable(Vertx vertx){
    this.vertx = vertx;
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  public void createTable() {

     final String SQL_CREATE_TABLE = "CREATE TABLE if NOT EXISTS service" +
      "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
      "name VARCHAR(255) NOT NULL," +
      "url VARCHAR(255) NOT NULL," +
      "updated_date_time VARCHAR(255) NOT NULL," +
      "status_response VARCHAR(255) NOT NULL" +
      " )";

    // Get connection to database
    Future<SQLConnection> sqlConnectionFuture = new DbUtils().getDBConnection();

    sqlConnectionFuture.onComplete(handler -> {
      SQLConnection connection = sqlConnectionFuture.result();

      // Create table with the connection
      connection.query(SQL_CREATE_TABLE, connhandler -> {
        if (connhandler.succeeded()) {
          System.out.println("Table created!");
        } else {
          System.out.println("Table creation failed!");
          connhandler.cause();
        }

      });

    });

  }

}
