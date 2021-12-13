package com.example.krycodetest.utils;

import com.example.krycodetest.database.ConnectDB;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class DbUtils {

  public Future<SQLConnection> getDBConnection() {

    JDBCClient jdbcClient = new ConnectDB(Vertx.vertx()).connectToDb();
    // Create a Future
    return Future.future(futHandler -> jdbcClient.getConnection(handler -> {
      if (handler.failed()) {
        System.err.println("Did not get the connection...: "
          + handler.cause().getMessage());
      } else {
        System.out.println("Got the connection to DB");

        // Get connection
        SQLConnection connection = handler.result();
        futHandler.complete(connection);

      }

    }));

  }

}
