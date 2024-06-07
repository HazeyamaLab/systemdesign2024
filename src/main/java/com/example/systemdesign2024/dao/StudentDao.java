package com.example.systemdesign2024.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import com.example.systemdesign2024.entity.Student;

public class StudentDao {
  private final MyJdbcTemplate myJdbcTemplate;

  public StudentDao() {
    this.myJdbcTemplate = new MyJdbcTemplate();
  }

  public List<Student> getAll() throws DaoException {
    try {
      List<Map<String, Object>> items = this.myJdbcTemplate.get().queryForList("select * from `students`");
      List<Student> students = items
          .stream()
          .map((item) -> new Student(String.valueOf(item.get("id")), String.valueOf(item.get("name"))))
          .toList();
      return students;
    } catch (DataAccessException dataAccessException) {
      dataAccessException.printStackTrace();
      DaoException daoException = new DaoException("データベースとの通信中にエラーが発生しました。");
      throw daoException;
    }
  }

  public void createOne(Student student) throws DaoException {
    try {
      this.myJdbcTemplate.get().update("insert into `students` (`id`, `name`) values (?, ?)", student.getId(), student.getName());
    } catch (DataAccessException dataAccessException) {
      dataAccessException.printStackTrace();
      if (dataAccessException instanceof DuplicateKeyException) {
        DaoException daoException = new DaoException("学籍番号が同じ学生を複数登録することはできません。");
        throw daoException;
      }
      DaoException daoException = new DaoException("同じ");
      throw daoException;
    }
  }
}
