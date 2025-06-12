package servlet.students;

import java.io.IOException;
import java.net.URLEncoder;

import control.students.CreateStudent;
import control.students.CreateStudentInput;
import control.students.CreateStudentResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelUtil.Failure;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>/students/create`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "/students/create" })
public class CreateStudentServlet extends HttpServlet {

  // `<コンテキストパス>/students/create`に対するPOSTリクエストを処理する`doPost`メソッドを実装する。
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // リクエストの情報は、`HttpServletRequest`型の引数`req`に含まれる。
    // `req`から情報を得る際に使用する文字コードは`HttpServletRequest.setCharacterEncoding`メソッドで設定する。
    // すべてのサーブレットのすべてのメソッドにこの記述が必要。
    req.setCharacterEncoding("UTF-8");

    // リクエストに含まれるパラメータの値を得て、コントロールへの入力用のオブジェクトにまとめる。
    String id = req.getParameter("id");
    String name = req.getParameter("name");
    CreateStudentInput createStudentInput = new CreateStudentInput(id, name);

    // ビジネスロジック層の窓口である、ECBパターンにおけるコントロールをインスタンス化する。
    CreateStudent control = new CreateStudent();

    try {

      // コントロールに入力用のオブジェクトを渡して処理を実行し、その結果を得る。
      CreateStudentResult createStudentResult = control.execute(createStudentInput);

      // `<コンテキストパス>/students/show-all`に処理結果のメッセージを含むクエリパラメータを付けたURLにアクセスしてもらうように、レスポンスを返す。
      // レスポンスを受け取ったブラウザはこのURLに自動的にアクセスする。
      // クエリパラメータの値はapplication/x-www-form-urlencoded（いわゆるパーセントエンコーディング）で符号化されている必要があるため、`URLEncoder.encode`メソッドを用いて符号化の処理を行っている。
      resp.sendRedirect(req.getContextPath()
          + "/students/show-all?messageFromPrev=" + URLEncoder.encode(createStudentResult.message(), "UTF-8"));

    } catch (Failure failure) {

      // コントロールは`Failure`型の例外を投げるかもしれない。
      // 例外が投げられた場合はその時点で`try`句の文の実行は停止され、`catch`句の各文が実行されることになる。

      // 例外をログに出力する。
      req.getServletContext().log(failure.getMessage(), failure);

      // `<コンテキストパス>/students/show-all`に例外のメッセージを含むクエリパラメータを付けたURLにアクセスしてもらうように、レスポンスを返す。
      // レスポンスを受け取ったブラウザはこのURLに自動的にアクセスする。
      // クエリパラメータの値はapplication/x-www-form-urlencoded（いわゆるパーセントエンコーディング）で符号化されている必要があるため、`URLEncoder.encode`メソッドを用いて符号化の処理を行っている。
      resp.sendRedirect(req.getContextPath()
          + "/students/show-all?errorFromPrev=" + URLEncoder.encode(failure.getMessage(), "UTF-8")
          + "&lastInputId=" + URLEncoder.encode(id, "UTF-8")
          + "&lastInputName=" + URLEncoder.encode(name, "UTF-8"));

    }

  }

}
