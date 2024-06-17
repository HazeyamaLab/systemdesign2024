package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionCloser {
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
