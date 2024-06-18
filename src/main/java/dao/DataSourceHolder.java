package dao;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DAOで利用する{@link javax.sql.DataSource}を提供する。
 * 
 * このクラスのインスタンスをDAOの{@code private}フィールドに割り当てておくことで、DAOの各メソッドで接続を取得する処理を記述しやすくなる。
 * 
 * <pre>{@code
 * class SampleDao {
 *   // `DataSourceHolder.dataSource`をDAOのフィールドに割り当てておく。
 *   private final DataSource dataSource;
 *   public SampleDao() {
 *     this.dataSourceHolder = new DataSourceHolder().dataSource;
 *   }
 *   public List&lt;SampleEntity&gt; getAll() {
 *     Connection connection = null;
 *     try {
 *       // `dataSource`フィールドに割り当てた`DataSource`オブジェクトの`getConnection`メソッドを呼び出して`java.sql.Connection`型のオブジェクトを得る。
 *       connection = this.dataSource.getConnection();
 *       // 何らかの処理
 *     } catch (SQLException exception) {
 *       // 何らかの処理
 *     }
 *   }
 * }
 * }</pre>
 */
class DataSourceHolder {
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
