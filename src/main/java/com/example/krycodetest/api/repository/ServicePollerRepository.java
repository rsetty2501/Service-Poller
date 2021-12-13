package com.example.krycodetest.api.repository;

import com.example.krycodetest.api.model.Service;
import com.example.krycodetest.database.ConnectDB;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;

import java.util.List;

public class ServicePollerRepository{

  private final JDBCClient jdbcClient;

  private static final String SQL_SELECT_ALL = "SELECT id, name, url, updated_date_time, status_response FROM service";
  private static final String SQL_INSERT = "INSERT INTO service (name, url, updated_date_time, status_response) VALUES (?, ?, ?, ?)";
  private static final String SQL_UPDATE = "UPDATE service SET updated_date_time = ?, status_response = ? WHERE id=?";
  private static final String SQL_DELETE = "DELETE FROM service WHERE id=?";

  public ServicePollerRepository(){

    jdbcClient = new ConnectDB(Vertx.vertx()).connectToDb();

  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  public Future<List<JsonArray>> getAll() {

    // Query data
    return Future.future(handler -> {

      jdbcClient.query(SQL_SELECT_ALL, queryHandler -> {
        if (queryHandler.succeeded()) {
          System.out.println("Query Success");
          ResultSet resultSet = queryHandler.result();
          List<JsonArray> data = resultSet.getResults();

          handler.complete(data);

        } else {
          System.out.println("Query Failed");
          queryHandler.cause();

        }

      });

    });
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  public void save(Service service) {

    jdbcClient.queryWithParams(SQL_INSERT, new JsonArray().add(service.getName()).add(service.getUrl()).add(service.getUpdatedDateTime()).add(service.getStatusResponse()),handler -> {
      if (handler.succeeded()) {
        System.out.println("Data inserted");
      } else {
        System.out.println("Data insertion failed");
        handler.cause();
      }

    });

  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  public void delete(int id) {

    jdbcClient.queryWithParams(SQL_DELETE, new JsonArray().add(id), handler -> {
      if (handler.succeeded()) {
        System.out.println("Data deleted");
      } else {
        System.out.println("Data deletion failed");
        handler.cause();
      }

    });

  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  public void update(int id, String dateTime, String status) {

    jdbcClient.queryWithParams(SQL_UPDATE, new JsonArray().add(dateTime).add(status).add(id), handler -> {
      if (handler.succeeded()) {
        System.out.println("Data updated");
      } else {
        System.out.println("Data updating failed");
        handler.cause();
      }

    });

  }
}
