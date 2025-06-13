package control.students;

import dao.DaoException;
import dao.StudentDao;
import entity.Student;
import modelUtil.Failure;

public class GetOneStudent {

  /**
   * 指定された学籍番号の学生の情報を取得する。
   * 
   * @param id 取得したい学生の学籍番号
   * 
   * @return
   *         取得に成功した場合は、{@link GetOneStudentResult}型のオブジェクトを返す。
   *         該当する学生の情報がなかった場合は{@code null}を返す。
   * 
   * @throws Failure 取得に失敗した場合は、{@link Failure}型の例外を投げる。
   */
  public GetOneStudentResult execute(String id) throws Failure {
    try {

      // DAOをインスタンス化する。
      StudentDao studentDao = new StudentDao();

      // 指定された学籍番号の学生の情報をDAOから取得する。
      Student student = studentDao.getOneById(id);

      // 取得に成功したことを意味する情報を返す。
      GetOneStudentResult result = new GetOneStudentResult(student);
      return result;

    } catch (DaoException daoException) {

      // DAOは`DaoException`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 登録に失敗したことを意味する`Failure`型の例外を投げる。メッセージは「学生情報の取得に失敗しました。」というメッセージにDAOからの例外のメッセージを付け足したものにしている。
      throw new Failure("学生情報の取得に失敗しました。" + daoException.getMessage(), daoException);

    }
  }
}
