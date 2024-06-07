package com.example.systemdesign2024.control.students;

import com.example.systemdesign2024.entity.Student;

public class CreateStudentResult {
  private final boolean isOk;
  private final Student student;
  private final String message;

  public CreateStudentResult(boolean isOk, Student student, String message) {
    this.isOk = isOk;
    this.student = student;
    this.message = message;
  }

  public boolean isOk() {
    return isOk;
  }

  public Student getStudent() {
    return student;
  }

  public String getMessage() {
    return message;
  }
}
