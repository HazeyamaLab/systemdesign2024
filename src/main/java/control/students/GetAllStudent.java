package control.students;

import java.util.List;

import dao.DaoException;
import dao.StudentDao;
import entity.Student;
import modelUtil.Failure;

/** システムに登録されているすべての学生の情報を取得するコントロール。 */
public class GetAllStudent {

  /**
   * システムに登録されているすべての学生の情報を取得する。
   * 
   * @return 取得に成功した場合は、{@link GetAllStudentResult}型のオブジェクトを返す。
   * @throws Failure 取得に失敗した場合は、{@link Failure}型の例外を投げる。
   */
  public GetAllStudentResult execute() throws Failure {
    try {

      // DAOをインスタンス化する。
      StudentDao studentDao = new StudentDao();

      // システムに登録されている学生の情報をDAOから取得する。
      List<Student> students = studentDao.getAll();

      // 取得に成功したことを意味する情報を返す。
      GetAllStudentResult result = new GetAllStudentResult(students);
      return result;

    } catch (DaoException daoException) {

      // DAOは`DaoException`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 登録に失敗したことを意味する`Failure`型の例外を投げる。メッセージは「学生情報の取得に失敗しました。」というメッセージにDAOからの例外のメッセージを付け足したものにしている。
      throw new Failure("学生情報の取得に失敗しました。" + daoException.getMessage(), daoException);

    }
  }

}
