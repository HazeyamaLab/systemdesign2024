package dao;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSourceHolder {
  private static HikariConfig _hikariConfig;
  private static DataSource _dataSource;

  public final DataSource dataSource;

  public DataSourceHolder() {
    if (DataSourceHolder._hikariConfig == null) {
      DataSourceHolder._hikariConfig = new HikariConfig(
          this.getClass().getClassLoader().getResource("dataSource.properties").getPath());
    }

    if (DataSourceHolder._dataSource == null) {
      DataSourceHolder._dataSource = new HikariDataSource(DataSourceHolder._hikariConfig);
    }

    this.dataSource = DataSourceHolder._dataSource;
  }
}
