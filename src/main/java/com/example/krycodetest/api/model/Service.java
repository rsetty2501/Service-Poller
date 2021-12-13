package com.example.krycodetest.api.model;


public class Service {

  private final String name;
  private final String url;
  private final String updatedDateTime;
  private final String statusResponse;

  public Service(String name, String url, String updatedDateTime, String statusResponse) {

    this.name = name;
    this.url = url;
    this.updatedDateTime = updatedDateTime;
    this.statusResponse = statusResponse;

  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public String getUpdatedDateTime() {
    return updatedDateTime;
  }

  public String getStatusResponse() {
    return statusResponse;
  }

}
