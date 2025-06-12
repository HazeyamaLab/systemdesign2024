package control.students;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}への入力に用いるオブジェクト。
 * 登録する学生の情報をまとめる。
 */
// ゲッタだけのクラスを作りたい場合は、record構文を使うと少ないコード量で書くことができる。
public record CreateStudentInput(
    /** 登録する学生の学籍番号 */
    String id,
    /** 登録する学生の氏名 */
    String name) {
}
