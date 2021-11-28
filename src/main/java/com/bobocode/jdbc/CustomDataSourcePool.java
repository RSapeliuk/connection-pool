package com.bobocode.jdbc;

import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CustomDataSourcePool extends PGSimpleDataSource {

  private Queue<Connection> connectionPool;
  private final Integer CONNECTION_POOL = 10;

  @SneakyThrows
  public CustomDataSourcePool(String url, String user, String password) {
    super();
    setUrl(url);
    setUser(user);
    setPassword(password);
    connectionPool = new ConcurrentLinkedQueue<>();
    for (int i = 0; i < CONNECTION_POOL; i++) {
      var physicalConnection = super.getConnection();
      ConnectionProxy connectionProxy = new ConnectionProxy(physicalConnection, connectionPool);
      connectionPool.add(connectionProxy);
    }
  }

  @Override
  public Connection getConnection() throws SQLException {
    return connectionPool.poll();
  }
}
