package control.students;

import java.util.List;
import entity.Student;

public class GetAllStudentResult {
	public final List<Student> students;
	public final String message;

	public GetAllStudentResult(List<Student> students, String message) {
		this.students = students;
		this.message = message;
	}
}
