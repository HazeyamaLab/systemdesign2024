package control.students;

import entity.Student;

/**
 * {@link GetOneStudent#execute(GetOneStudentInput)}の結果の出力に用いるオブジェクト。
 * 取得した学生の情報を持つ。
 */
// ゲッタだけのクラスを作りたい場合は、record構文を使うと少ないコード量で書くことができる。
public record GetOneStudentResult(
    /**
     * 取得した学生の情報。
     * 該当する学生の情報がなければ{@code null}が割り当てられる。
     */
    Student student) {
}
