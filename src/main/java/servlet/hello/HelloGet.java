package servlet.hello;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>`または`<コンテキストパス>/hello-get`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "", "/hello-get" })
public class HelloGet extends HttpServlet {

  // `<コンテキストパス>/hello-get`に対するGETリクエストを処理する`doGet`メソッドを実装する。
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // 【フォワード】
    // 本来はこのサーブレットからレスポンスを返すところであるが、JSPを利用してHTML文書のレスポンスを返す動作を「フォワード」という。
    // レスポンスを返す処理をJSPに転送する。
    req.getRequestDispatcher("/WEB-INF/hello/hello.jsp").forward(req, resp);

  }

}
