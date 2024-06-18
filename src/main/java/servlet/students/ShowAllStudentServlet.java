package servlet.students;

import java.io.IOException;

import control.students.GetAllStudent;
import control.students.GetAllStudentResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelUtil.Failure;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>/students/show-all`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "/students/show-all" })
public class ShowAllStudentServlet extends HttpServlet {

  // `<コンテキストパス>/students/show-all`に対するGETリクエストを処理する`doGet`メソッドを実装する。
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // リクエストの情報は、`HttpServletRequest`型の引数`req`に含まれる。
    // `req`から情報を得る際に使用する文字コードは`HttpServletRequest.setCharacterEncoding`メソッドで設定する。
    // すべてのサーブレットのすべてのメソッドにこの記述が必要。
    req.setCharacterEncoding("UTF-8");

    // ビジネスロジック層の窓口である、ECBパターンにおけるコントロールをインスタンス化する。
    GetAllStudent control = new GetAllStudent();

    try {

      // コントローラの処理を実行し、その結果を得る。
      GetAllStudentResult getAllStudentResult = control.execute();

      // 得た結果をリクエストスコープの属性に割り当てる。
      req.setAttribute("students", getAllStudentResult.students);

    } catch (Failure failure) {

      // コントローラは`Failure`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 例外をログに出力する。
      req.getServletContext().log(failure.getMessage(), failure);

      // 例外のメッセージをリクエストスコープの属性に割り当てる。
      req.setAttribute("error", failure.getMessage());

    }

    // 本来はサーブレットからレスポンスを返すが、この例ではJSPを利用してレスポンスをHTML文書として返す。
    // レスポンスを返す処理をJSPに転送する。
    req.getRequestDispatcher("/WEB-INF/students/show-all.jsp").forward(req, resp);

  }

}
