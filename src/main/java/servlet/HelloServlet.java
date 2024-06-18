package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>/`または`<コンテキストパス>/hello-servet`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "/", "/hello-servlet" })
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // リクエストの情報は、`HttpServletRequest`型の引数`req`に含まれる。
    // `req`から情報を得る際に使用する文字コードは`HttpServletRequest.setCharacterEncoding`メソッドで設定する。
    // すべてのサーブレットのすべてのメソッドにこの記述が必要。
    req.setCharacterEncoding("UTF-8");

    // 本来はサーブレットからレスポンスを返すが、この例ではJSPを利用してレスポンスをHTML文書として返す。
    // レスポンスを返す処理をJSPに転送する。
    req.getRequestDispatcher("/WEB-INF/hello-jsp.jsp").forward(req, resp);

  }
}
