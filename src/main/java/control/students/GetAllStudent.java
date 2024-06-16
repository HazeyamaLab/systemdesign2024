package control.students;

import java.util.List;

import dao.DaoException;
import dao.StudentDao;
import entity.Student;
import modelUtil.Failure;

public class GetAllStudent {
  public GetAllStudentResult execute() throws Failure {
    StudentDao studentDao = new StudentDao();
    try {
      List<Student> students = studentDao.getAll();
      GetAllStudentResult result = new GetAllStudentResult(students, null);
      return result;
    } catch (DaoException daoException) {
      throw new Failure("学生情報の取得に失敗しました。" + daoException.getMessage(), daoException);
    }
  }
}
