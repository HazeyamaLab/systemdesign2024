package control.students;

import entity.Student;

/**
 * {@link GetOneStudent#execute(GetOneStudentInput)}の結果の出力に用いるオブジェクト。
 * 取得した学生の情報を持つ。
 */
public class GetOneStudentResult {
  // このクラスから作成されたオブジェクトはコントロールからの結果の出力の際に一度用いられるのみなので、各フィールドが変更可能である必要はない。
  // そこで、`private`修飾子付きのフィールドとゲッターメソッドを定義する代わりに、
  // 各フィールドに`final`修飾子を付けることで、読み取り専用の変更不可能なフィールドを定義している。

  /** 
   * 取得した学生の情報。
   * 該当する学生の情報がなければ{@code null}が割り当てられる。
   */
  public final Student student;

	public GetOneStudentResult(Student student) {
		this.student = student;
	}
}
