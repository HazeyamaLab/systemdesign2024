package servlet.hello;

import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// サーブレットは`HttpServlet`のサブクラスとして実装し、`@WebServlet`アノテーションのvalue属性でどのパスに対するリクエストを処理するのかを指定する。
// この場合、`<コンテキストパス>/`または`<コンテキストパス>/hello-post`に対するリクエストを処理するサーブレットになる。
@WebServlet(value = { "/hello-post2" })
public class HelloPost2 extends HttpServlet {

  // `<コンテキストパス>/hello-post`に対するPOSTリクエストを処理する`doPost`メソッドを実装する。
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // リクエストの情報は、`HttpServletRequest`型の引数`req`に含まれる。
    // `req`から情報を得る際に使用する文字コードは`HttpServletRequest.setCharacterEncoding`メソッドで設定する。
    // すべてのサーブレットのすべてのメソッドにこの記述が必要。
    req.setCharacterEncoding("UTF-8");

    // 【リダイレクト】
    // このサーブレットから「指定したURLにアクセスせよ」というレスポンスを返して、ブラウザに自動的にそのURLにアクセスさせる動作を「リダイレクト」という。
    // POSTリクエストに対してそのままHTML文書を返す（つまり「フォワード」する）と、ブラウザが再読み込みをした場合に、ブラウザが同じPOSTリクエストを送信し、意図せずに同じ処理が何度も行われるバグを引き起こす可能性がある。
    // そのため、POSTリクエストに対してはリダイレクトを返し、別のGETメソッドを処理するサーブレットに対応したURLにアクセスさせることが望ましい。
    // この例では、`<コンテキストパス>/hello-get`にparameterInPostRequestパラメータの値を付けたURLにアクセスしてもらうように、レスポンスを返す。
    // レスポンスを受け取ったブラウザはこのURLに自動的にアクセスする。
    // クエリパラメータの値はapplication/x-www-form-urlencoded（いわゆるパーセントエンコーディング）で符号化されている必要があるため、`URLEncoder.encode`メソッドを用いて符号化の処理を行っている。
    resp.sendRedirect(
        req.getContextPath() + "/hello-get?parameterInGetRequest="
            + URLEncoder.encode(req.getParameter("parameterInPostRequest"), "UTF-8"));

  }

}
