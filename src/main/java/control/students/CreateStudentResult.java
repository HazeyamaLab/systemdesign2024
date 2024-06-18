package control.students;

import entity.Student;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}の結果の出力に用いるオブジェクト。
 * 登録された学生の情報を表すエンティティオブジェクトと学生の登録の結果をまとめる。
 */
public class CreateStudentResult {
  // このクラスから作成されたオブジェクトはコントローラへの入力の際に一度用いられるのみなので、各フィールドが変更可能である必要はない。
  // そこで、`private`修飾子付きのフィールドとゲッターメソッドを定義する代わりに、
  // 各フィールドに`final`修飾子を付けることで、読み取り専用の変更不可能なフィールドを定義している。

  /** 登録された学生の情報を表すエンティティオブジェクト。 */
  public final Student student;
  /** 学生の情報を登録した結果を表すメッセージ。 */
  public final String message;

  public CreateStudentResult(Student student, String message) {
    this.student = student;
    this.message = message;
  }
}
