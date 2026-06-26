package servlet.hello;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = { "/hello-servlet2" })
public class HelloServlet2 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setStatus(200);
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html");

    // 【フォワード】
    // 本来はこのサーブレットからレスポンスを返すところであるが、JSPを利用してHTML文書のレスポンスを返す動作を「フォワード」という。
    // レスポンスを返す処理をJSPに転送する。
    req.getRequestDispatcher("/WEB-INF/hello/hello-servlet.jsp").forward(req, resp);

  }

}
