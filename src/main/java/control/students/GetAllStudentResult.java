package control.students;

import java.util.List;
import entity.Student;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}の結果の出力に用いるオブジェクト。
 * システムに登録されている学生の情報の一覧を持つ。
 */
public class GetAllStudentResult {
  // このクラスから作成されたオブジェクトはコントローラへの入力の際に一度用いられるのみなので、各フィールドが変更可能である必要はない。
  // そこで、`private`修飾子付きのフィールドとゲッターメソッドを定義する代わりに、
  // 各フィールドに`final`修飾子を付けることで、読み取り専用の変更不可能なフィールドを定義している。

	/** システムに登録されているすべての学生の情報のリスト。 */
	public final List<Student> students;

	public GetAllStudentResult(List<Student> students) {
		this.students = students;
	}
}
