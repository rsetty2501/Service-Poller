package com.example.krycodetest;

import com.example.krycodetest.database.CreateTable;
import com.example.krycodetest.verticle.MainVerticle;
import io.vertx.core.Vertx;

public class StartServicePoller {

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();

    // Connect to Database and create Table
    CreateTable createDBTable = new CreateTable(vertx);
    createDBTable.createTable();

    // Deploy the MainVerticle
    vertx.deployVerticle(new MainVerticle());

  }

}
