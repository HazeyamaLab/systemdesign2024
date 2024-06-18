package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * {@link java.sql.Connection#close()}を抽象化する。
 * 
 * このクラスのインスタンスをDAOの{@code private}フィールドに割り当てておくことで、DAOの各メソッドで接続を閉じる処理を記述しやすくなる。
 * 
 * <pre>{@code
 * class SampleDao {
 *   // `ConnectionCloser`型のオブジェクトをDAOのフィールドに割り当てておく。
 *   private final ConnectionCloser connectionCloser;
 *   public SampleDao() {
 *     this.connectionCloser = new ConnectionCloser();
 *   }
 *   public List&lt;SampleEntity&gt; getAll() {
 *     Connection connection = null;
 *     try {
 *       // 何らかの処理
 *     } catch (SQLException exception) {
 *       // 何らかの処理
 *     } finally {
 *       // `try`句および`catch`句から値を返したり例外を投げたりする前に接続を閉じる。
 *       this.connectionCloser.close(connection);
 *     }
 *   }
 * }
 * }</pre>
 */
class ConnectionCloser {
  /**
   * {@link java.sql.Connection#close()}を抽象化する。
   * 引数の{@link java.sql.Connection}型のオブジェクトに対して{@code close}メソッドを呼び出す。
   * 万が一エラーが発生した場合は{@link DaoException}を投げる。
   */
  public void closeConnection(Connection connection) throws DaoException {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException sqlException) {
        throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException);
      }
    }
  }
}
