package control.students;

import dao.DaoException;
import dao.StudentDao;
import entity.Student;
import modelUtil.Failure;

/** 学生の情報を登録するコントロール。 */
public class CreateStudent {

  /**
   * 学生の情報を登録する。
   * 
   * @param id 登録する学生の学籍番号
   * @param name 登録する学生の氏名
   * @return 登録に成功した場合は、{@link CreateStudentResult}型のオブジェクトを返す。
   * @throws Failure 登録に失敗した場合は、{@link Failure}型の例外を投げる。
   */
  public CreateStudentResult execute(String id, String name) throws Failure {
    try {

      // 入力の情報を用いて、学生の情報のエンティティオブジェクトをインスタンス化する。
      Student student = new Student(id, name);

      // DAOをインスタンス化する。
      StudentDao studentDao = new StudentDao();

      // 学生の情報のエンティティオブジェクトをDAOに渡して登録する。
      studentDao.createOne(student);

      // 登録に成功したことを意味する情報を返す。
      CreateStudentResult result = new CreateStudentResult(student, "OK牧場！（学生情報の登録に成功しました）");
      return result;

    } catch (Failure failure) {

      // `Student`のコンストラクタは`Failure`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 登録に失敗したことを意味する`Failure`型の例外を投げる。メッセージは「NG牧場……」というメッセージにDAOからの例外のメッセージを付け足したものにしている。
      throw new Failure("NG牧場……（学生情報の登録に失敗しました）" + failure.getMessage(), failure);

    } catch (DaoException daoException) {

      // DAOは`DaoException`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 登録に失敗したことを意味する`Failure`型の例外を投げる。メッセージは「NG牧場……」というメッセージにDAOからの例外のメッセージを付け足したものにしている。
      throw new Failure("NG牧場……（学生情報の登録に失敗しました）" + daoException.getMessage(), daoException);

    }
  }

}
