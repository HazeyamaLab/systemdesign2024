package control.students;

import java.util.List;
import entity.Student;

/**
 * {@link CreateStudent#execute(CreateStudentInput)}の結果の出力に用いるオブジェクト。
 * システムに登録されている学生の情報の一覧を持つ。
 */
// ゲッタだけのクラスを作りたい場合は、record構文を使うと少ないコード量で書くことができる。
public record GetAllStudentResult(
    /** システムに登録されているすべての学生の情報のリスト。 */
    List<Student> students) {
}
