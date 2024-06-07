package com.example.systemdesign2024.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.util.DriverDataSource;

public class MyJdbcTemplate {
  private final String url = "jdbc:mysql://mysql:3306/database?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Tokyo&rewriteBatchedStatements=true";
  private final String username = "mysql";
  private final String password = "password";
  private final String driverClassName = "com.mysql.cj.jdbc.Driver";
  private final JdbcTemplate jdbcTemplate;

  public MyJdbcTemplate() {
    DataSource dataSource = new DriverDataSource(url, driverClassName, new Properties(), username, password);
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public JdbcTemplate get() {
    return this.jdbcTemplate;
  }
}
