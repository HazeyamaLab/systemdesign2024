package servlet.students;

import java.io.IOException;

import control.students.GetOneStudent;
import control.students.GetOneStudentInput;
import control.students.GetOneStudentResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelUtil.Failure;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>/students/show-one`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "/students/show-one" })
public class ShowOneStudentServlet extends HttpServlet {

  // `<コンテキストパス>/students/show-one`に対するGETリクエストを処理する`doGet`メソッドを実装する。
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // リクエストの情報は、`HttpServletRequest`型の引数`req`に含まれる。
    // `req`から情報を得る際に使用する文字コードは`HttpServletRequest.setCharacterEncoding`メソッドで設定する。
    // すべてのサーブレットのすべてのメソッドにこの記述が必要。
    req.setCharacterEncoding("UTF-8");

    String id = req.getParameter("id");
    GetOneStudentInput getOneStudentInput = new GetOneStudentInput(id);

    // ビジネスロジック層の窓口である、ECBパターンにおけるコントロールをインスタンス化する。
    GetOneStudent control = new GetOneStudent();

    try {

      // コントロールに入力用のオブジェクトを渡して処理を実行し、その結果を得る。
      GetOneStudentResult getOneStudentResult = control.execute(getOneStudentInput);

      // 得た結果をリクエストスコープの属性に割り当てる。
      req.setAttribute("student", getOneStudentResult.student());

    } catch (Failure failure) {

      // コントロールは`Failure`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 例外をログに出力する。
      req.getServletContext().log(failure.getMessage(), failure);

      // 例外のメッセージをリクエストスコープの属性に割り当てる。
      req.setAttribute("error", failure.getMessage());

    }

    // 本来はサーブレットからレスポンスを返すが、この例ではJSPを利用してレスポンスをHTML文書として返す。
    // レスポンスを返す処理をJSPに転送する。
    req.getRequestDispatcher("/WEB-INF/students/show-one.jsp").forward(req, resp);

  }
}
