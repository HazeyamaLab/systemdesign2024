package com.example.systemdesign2024.control.students;

import java.util.List;
import com.example.systemdesign2024.entity.Student;

public class GetAllStudentResult {
  private boolean isOk;
  private List<Student> students;
  private String message;

	public GetAllStudentResult(boolean isOk, List<Student> students, String message) {
		this.isOk = isOk;
		this.students = students;
		this.message = message;
	}

	public boolean isOk() {
		return isOk;
	}

	public List<Student> getStudents() {
		return students;
	}

	public String getMessage() {
		return message;
	}
}
