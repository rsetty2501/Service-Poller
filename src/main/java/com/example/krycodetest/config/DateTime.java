package com.example.krycodetest.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

  public String getCurrDateTime() {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    return formatter.format(date);

  }

}
