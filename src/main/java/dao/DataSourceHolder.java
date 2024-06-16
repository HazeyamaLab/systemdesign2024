package dao;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceHolder {
  private static HikariConfig hikariConfig;
  private static DataSource dataSource;

  public DataSourceHolder() {
    if (DataSourceHolder.hikariConfig == null) {
      DataSourceHolder.hikariConfig = new HikariConfig(
          this.getClass().getClassLoader().getResource("dataSource.properties").getPath());
    }
    if (DataSourceHolder.dataSource == null) {
      dataSource = new HikariDataSource(DataSourceHolder.hikariConfig);
    }
  }

  public DataSource getDataSource() {
    return DataSourceHolder.dataSource;
  }
}
