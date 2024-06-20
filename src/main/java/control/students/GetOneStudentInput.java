package control.students;

/**
 * {@link GetOneStudent#execute(GetOneStudentInput)}への入力に用いるオブジェクト。
 * どの学生の情報を取得するのかをまとめる。
 */
public class GetOneStudentInput {
  // このクラスから作成されたオブジェクトはコントロールへの入力の際に一度用いられるのみなので、各フィールドが変更可能である必要はない。
  // そこで、`private`修飾子付きのフィールドとゲッターメソッドを定義する代わりに、
  // 各フィールドに`final`修飾子を付けることで、読み取り専用の変更不可能なフィールドを定義している。

  /** 取得したい学生の学籍番号。 */
  public final String id;

	public GetOneStudentInput(String id) {
		this.id = id;
	}
}
