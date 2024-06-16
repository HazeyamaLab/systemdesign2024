package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import entity.Student;
import modelUtil.Failure;

/** 学生の情報を扱うDAO。 */
public class StudentDao {
  private final DataSource dataSorce;

  public StudentDao() {
    // MySQLサーバーにアクセスするためには`DataSource.getConnection`メソッドで`Connection`オブジェクトを得る必要があるので、
    // `dataSource`フィールドに`DataSource`型のオブジェクトを割り当てておく。
    // `DataSource`型のオブジェクトは`DataSourceHolder.getDataSource`メソッドで得られる。
    this.dataSorce = new DataSourceHolder().getDataSource();
  }

  /**
   * システムに登録されているすべての学生の情報を取得する。
   * 
   * @return 学生の情報を表す{@link Student}オブジェクトの{@link List}を返す。
   * @throws DaoException 取得に失敗した場合は{@link DaoException}を投げる。
   */
  public List<Student> getAll() throws DaoException {
    Connection connection = null;

    try {

      // MySQLサーバーにアクセスするための`Connection`オブジェクトを得る。
      connection = dataSorce.getConnection();

      // 実行するSQL文を準備する。
      PreparedStatement preparedStatement = connection.prepareStatement("select * from `students`");

      // SQL文を実行する。
      // 取得系のSQL文を実行する際は`PreparedStatement.executeQuery`メソッドを用いる。
      // 実行した結果は`ResultSet`型のオブジェクトとして得られる。
      ResultSet resultSet = preparedStatement.executeQuery();

      // `ResultSet`型で表される取得結果を`List<Student>`型のリストに変換してから返す。
      // `ResultSet`型はポインタ付きの表のようになっている。
      // `ResultSet.next`メソッドでポインタを次の行に進めながら、ゲッターメソッドで各列の値を取得して`Student`型のオブジェクトにまとめて、それをリストに追加することを繰り返す。
      List<Student> students = new LinkedList<>();
      while (resultSet.next()) {
        students.add(new Student(resultSet.getString("id"), resultSet.getString("name")));
      }

      // 値を返す前に必ず`Connection`を閉じること。
      // 閉じるのを忘れた場合、アプリケーションとMySQLサーバーの間の同時接続数の上限を超過してエラーになることがある。
      connection.close();

      return students;

    } catch (Failure failure) {

      // `Student`クラスのコンストラクタは`Failure`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`節の文の実行は停止され、`catch`節の各文が実行されることになる。

      // 値を返したり例外を投げたりする前に、必ず`Connection`を閉じること。
      // 閉じるのを忘れた場合、アプリケーションとMySQLサーバーの間の同時接続数の上限を超過してエラーになることがある。
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException sqlException) {
        throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException);
      }

      // `DaoException`型の例外を投げる。
      throw new DaoException("データベースに不正なデータが含まれているため、処理を中断しました。", failure);

    } catch (SQLException sqlException) {

      // SQL関連のメソッドは`SQLException`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`節の文の実行は停止され、`catch`節の各文が実行されることになる。

      // 値を返したり例外を投げたりする前に、必ず`Connection`を閉じること。
      // 閉じるのを忘れた場合、アプリケーションとMySQLサーバーの間の同時接続数の上限を超過してエラーになることがある。
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException sqlException2) {
        throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException2);
      }

      // `DaoException`型の例外を投げる。
      throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException);

    }
  }

  /**
   * 学生の情報を登録する。
   * 
   * @param student 登録する学生の情報を表すエンティティオブジェクト。
   * @throws DaoException 登録に失敗した場合は{@link DaoException}を投げる。
   */
  public void createOne(Student student) throws DaoException {
    Connection connection = null;

    try {

      // MySQLサーバーにアクセスするための`Connection`オブジェクトを得る。
      connection = dataSorce.getConnection();

      // SQL文を実行する。実行した結果は`ResultSet`型のオブジェクトとして得られる。
      // 引数によってSQL文を変えたい場合は、変えたい部分を`?`としておく。
      PreparedStatement preparedStatement = connection
          .prepareStatement("insert into `students` (`id`, `name`) values (?, ?)");

      // 1つ目の`?`には`student`の`id`フィールドの値を割り当てる。
      preparedStatement.setString(1, student.getId());

      // 2つ目の`?`には`student`の`name`フィールドの値を割り当てる。
      preparedStatement.setString(2, student.getName());

      // SQL文を実行する。
      // 作成、更新、削除系のSQL文を実行する際は`PreparedStatement.executeUpdate`メソッドを用いる。
      preparedStatement.executeUpdate();

      // 必ず`Connection`を閉じること。
      // 閉じるのを忘れた場合、アプリケーションとMySQLサーバーの間の同時接続数の上限を超過してエラーになることがある。
      connection.close();

    } catch (SQLException sqlException) {

      // SQL関連のメソッドは`SQLException`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`節の文の実行は停止され、`catch`節の各文が実行されることになる。

      // 値を返したり例外を投げたりする前に、必ず`Connection`を閉じること。
      // 閉じるのを忘れた場合、アプリケーションとMySQLサーバーの間の同時接続数の上限を超過してエラーになることがある。
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException sqlException2) {
        throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException2);
      }

      // 制約が設定されているカラムがテーブルに含まれている場合で、その制約に反するSQL文を実行しようとした場合、
      // `PreparedStatement.executeUpdate`メソッドは`SQLIntegrityConstraintViolationException`を投げる。
      // この例では、得られた例外が`SQLIntegrityConstraintViolationException`型であった場合は、専用のメッセージで`DaoException`型の例外を投げるようにしている。
      if (sqlException instanceof SQLIntegrityConstraintViolationException) {
        throw new DaoException("学籍番号が同じ学生を複数登録することはできません。", sqlException);
      }

      // `DaoException`型の例外を投げる。
      throw new DaoException("データベースとの通信中にエラーが発生しました。", sqlException);

    }
  }
}
