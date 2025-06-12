package control.students;

/**
 * {@link GetOneStudent#execute(GetOneStudentInput)}への入力に用いるオブジェクト。
 * どの学生の情報を取得するのかをまとめる。
 */
// ゲッタだけのクラスを作りたい場合は、record構文を使うと少ないコード量で書くことができる。
public record GetOneStudentInput(
    /** 取得したい学生の学籍番号。 */
    String id) {
}
