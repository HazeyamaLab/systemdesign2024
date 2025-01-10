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
 * 
 *   public SampleDao() {
 *     this.dataSourceHolder = new DataSourceHolder().dataSource;
 *   }
 * 
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
    // 環境変数`APP_DB_PROPERTIES_FILENAME`がある場合は、`src/main/resources`配下のそのファイルからDBの接続設定を取得する。
    String propertiesFileName = System.getenv("APP_DB_PROPERTIES_FILENAME");
    if (propertiesFileName == null) {
      propertiesFileName = "dataSource.properties";
    }

    // 環境変数`APP_DB_PROPERTIES_FILEPATH`がある場合は、そのパスのファイルからDBの接続設定を取得する。
    // この環境変数での設定は`APP_DB_PROPERTIES_FILENAME`での設定に優先する。
    String propertiesFilePath = System.getenv("APP_DB_PROPERTIES_FILEPATH");
    if (propertiesFilePath == null) {
      propertiesFilePath = this.getClass().getClassLoader().getResource(propertiesFileName).getPath();
    }

    if (DataSourceHolder._hikariConfig == null) {
      DataSourceHolder._hikariConfig = new HikariConfig(propertiesFilePath);
    }

    if (DataSourceHolder._dataSource == null) {
      DataSourceHolder._dataSource = new HikariDataSource(DataSourceHolder._hikariConfig);
    }

    this.dataSource = DataSourceHolder._dataSource;
  }
}
