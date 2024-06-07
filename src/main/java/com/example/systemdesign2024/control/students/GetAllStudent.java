package com.example.systemdesign2024.control.students;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.systemdesign2024.dao.DaoException;
import com.example.systemdesign2024.dao.StudentDao;
import com.example.systemdesign2024.entity.Student;

@Service
public class GetAllStudent {
  public GetAllStudentResult execute() {
    try {
      StudentDao studentDao = new StudentDao();
      List<Student> students = studentDao.getAll();
      GetAllStudentResult result = new GetAllStudentResult(true, students, null);
      return result;
    } catch(DaoException daoException) {
      GetAllStudentResult result = new GetAllStudentResult(false, null, daoException.getMessage());
      return result;
    }
  }
}
