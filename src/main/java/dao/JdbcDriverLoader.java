package dao;

/**
 * JDBC Driverを読み込む。
 */
class JdbcDriverLoader {
  static void load() {
    try {
      // この行がないと、ビルド時のclasspathにConnector/Jが乗らなくなってしまう。
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }
}
