package control.students;

import entity.Student;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}の結果の出力に用いるオブジェクト。
 * 登録された学生の情報を表すエンティティオブジェクトと学生の登録の結果をまとめる。
 */
// ゲッタだけのクラスを作りたい場合は、record構文を使うと少ないコード量で書くことができる。
public record CreateStudentResult(
    /** 登録された学生の情報を表すエンティティオブジェクト。 */
    Student student,
    /** 学生の情報を登録した結果を表すメッセージ。 */
    String message) {
}
