package com.example.systemdesign2024.control.students;

import com.example.systemdesign2024.dao.DaoException;
import com.example.systemdesign2024.dao.StudentDao;
import com.example.systemdesign2024.entity.Student;

public class CreateStudent {
  public CreateStudentResult execute(CreateStudentInput input) {
    try {
      Student student = new Student(input.getId(), input.getName());
      StudentDao studentDao = new StudentDao();
      studentDao.createOne(student);
      CreateStudentResult result = new CreateStudentResult(true, student, "学生の登録に成功しました。");
      return result;
    } catch (DaoException exception) {
      CreateStudentResult result = new CreateStudentResult(false, null, exception.getMessage());
      return result;
    }
  }
}
