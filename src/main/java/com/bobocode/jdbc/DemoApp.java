package com.bobocode.jdbc;

import lombok.SneakyThrows;

import javax.sql.DataSource;

public class DemoApp {
  private static DataSource dataSource;

  @SneakyThrows
  public static void main(String[] args) {
    initializeDataSource();

    var start = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
      try(var connection = dataSource.getConnection()){
        // checking connection time
      }

    }
    var endTime = System.nanoTime();
    System.out.println("Time spent: " + (endTime-start)/1000000 + " millis");
  }

  private static void initializeDataSource() {
    CustomDataSourcePool dataSourcePool = new CustomDataSourcePool(
        "jdbc:postgresql://localhost:7070/postgres",
        "postgres",
        "password"
    );
    dataSource = dataSourcePool;
  }
}
