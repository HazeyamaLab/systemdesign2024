package control.students;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}への入力に用いるオブジェクト。
 * 登録する学生の情報をまとめる。
 */
public class CreateStudentInput {
  // このクラスから作成されたオブジェクトはコントローラへの入力の際に一度用いられるのみなので、各フィールドが変更可能である必要はない。
  // そこで、`private`修飾子付きのフィールドとゲッターメソッドを定義する代わりに、
  // 各フィールドに`final`修飾子を付けることで、読み取り専用の変更不可能なフィールドを定義している。

  /** 登録する学生の学籍番号。 */
  public final String id;
  /** 登録する学生の氏名。 */
  public final String name;

  public CreateStudentInput(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
