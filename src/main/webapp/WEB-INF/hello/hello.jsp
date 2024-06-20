<%-- 文字コードの設定。すべてのJSPファイルにこの記述が必要。JSPの文字コードと1行目で設定した文字コードが一致していないと文字化けする。 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%-- JSTL（Jakarta Standard Tag Library）の読み込み。JSTLを用いる場合はこの記述が必要。 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hello Servlet and JSP!</title>
  <link rel="stylesheet" href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/index.css">
</head>

<body>
  <h1>Hello Servlet and JSP!</h1>

  <%--
    【パラメータの値を取得する】
    サーブレットの`HttpServletRequest.getParameter`メソッドで得られる値は、EL式の中では`param.<パラメータ名>`として得ることができる。
  --%>
  <dl>
    <dt>parameterInGetRequestパラメータの値</dt>
    <dd>${fn:escapeXml(param.parameterInGetRequest)}</dd>
    <dt>parameterInPostRequestパラメータの値</dt>
    <dd>${fn:escapeXml(param.parameterInPostRequest)}</dd>
  </dl>

  <h2>リンク</h2>
  <ul>
    <li>
      <%--
        【ハイパーリンク】
        ハイパーリンクは`a`要素で用意する。
        `href`属性でリンク先のURLを指定する。
      --%>
      <%--
        【コンテキストパスを取得する】
        サーブレットに処理をさせるためのリクエストの送信先のURLのパス部分はコンテキストパスで始まる。
        したがって、リンク先やフォームの送信先に使用するURLを絶対パスで指定する場合は、先頭にコンテキストパスを付ける必要がある。
        EL式の中では`pageContext.servletContext.getContextPath()`でコンテキストパスを得ることができる。
      --%>
      <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-get">/systemdesign2024/hello-get にアクセスする</a>
    </li>
    <li>
      <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-post">/systemdesign2024/hello-post にアクセスする</a>
    </li>
    <li>
      <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-get?parameterInGetRequest=You_clicked_the_hyperlink">/systemdesign2024/hello-get?parameterInGetRequest=You_clicked_the_hyperlink にアクセスする</a>
    </li>
  </ul>

  <h2>/systemdesign2024/hello-get にGETリクエストを送信するためのフォーム</h2>
  <%--
    【フォーム】
    ユーザからWebアプリケーションに情報を送信させるときは、HTTPリクエストを送信させる。
    `form`要素を用意することで、ユーザは`form`要素内の`input`要素を操作してリクエストの内容を編集したり、`button`要素をクリックしてHTTPリクエストを送信したりすることができるようになる。
    HTTPリクエストの送信先のURLは`form`要素の`action`属性で指定する。
    HTTPリクエストのメソッドは`form`要素の`method`属性で指定する。ユーザからWebアプリケーションに情報を送信させるときは、原則としてPOSTリクエストを送信させる。

    この例では、`servlet.students.HelloGet.doGet`メソッドに処理させるために、
    `action`属性に`<コンテキストパス>/hello-get`を、`method`属性に`get`を指定している。
  --%>
  <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-get" method="get">
    <p>
      <%--
        【パラメータ・ユーザにパラメータの値を編集させるための`input`要素】
        `form`要素の内部に`input`要素を作成することで、`input`要素ごとに、対応する内容が「パラメータ」としてリクエストに含まれるようになる。
        パラメータはパラメータ名と値の組み合わせであり、パラメータ名には`name`属性に指定した値が使われ、パラメータの値には`input`要素に入力された値が使われる。
        `input`要素の初期値は`value`属性で指定できる。
        リクエストを処理するサーブレットでは、各パラメータの値を`HttpRequestServlet.getParameter`メソッドで得ることができる。
      --%>
      <label>入力：<input type="text" name="parameterInGetRequest" /></label>
    </p>

    <%--
      【button要素・ユーザーにHTTPリクエストを送信させるためのボタン】
      `form`要素の内部に`type="submit"`属性を持つ`button`要素を作成することで、そのボタンが押された時にフォームに入力された内容が送信されるようになる。
    --%>
    <button type="submit">GETリクエストを送信</button>
  </form>

  <h2>/systemdesign2024/hello-post にPOSTリクエストを送信するためのフォーム</h2>
  <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-post" method="post">
    <p>
      <label>入力：<input type="text" name="parameterInPostRequest" /></label>
    </p>
    <button type="submit">POSTリクエストを送信</button>
  </form>

  <h2>/systemdesign2024/hello-post2 にPOSTリクエストを送信するためのフォーム</h2>
  <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/hello-post2" method="post">
    <p>
      <label>入力：<input type="text" name="parameterInPostRequest" /></label>
    </p>
    <button type="submit">POSTリクエストを送信</button>
  </form>

  <h2>他のサンプル</h2>
  <ul>
    <li>
      <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/students/show-all">stuinfo</a>
    </li>
  </ul>
</body>

</html>
