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
  <title>学生一覧</title>
  <link rel="stylesheet" href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/index.css">
</head>

<body>
  <h1>学生一覧</h1>

  <%--
    【`<c:if>`タグで要素を出力するかどうかを制御する】
    `<c:if>`タグを使うと、`test`属性で指定した条件式が真である場合にのみ、中身を出力することができる。
  --%>
  <%--
    【リクエストスコープの属性値を取得する】
    サーブレットの`HttpServletRequest.setAttribute`メソッドでリクエストスコープの属性に割り当てたJavaオブジェクトの値は、EL式の中では`requestScope.<属性名>`として得ることができる。
  --%>
  <c:if test="${requestScope.error != null}">
    <%--
      【Javaオブジェクトの値を出力する・XSS対策としてのエスケープ処理】
      Javaオブジェクトの値を出力に含める場合は、XSS対策として、必ずHTML構文として意味を持つ文字をエスケープしておくこと。
      EL式の中では、`fn:escapeXml`関数に文字列を渡すと、エスケープ処理後の文字列を得られる。
    --%>
    <div class="error">エラー：${fn:escapeXml(requestScope.error)}</div>
  </c:if>

  <%--
    【パラメータの値を取得する】
    サーブレットの`HttpServletRequest.getParameter`メソッドで得られる値は、EL式の中では`param.<パラメータ名>`として得ることができる。
  --%>
  <c:if test="${param.messageFromPrev != null}">
    <div class="message">メッセージ：${fn:escapeXml(param.messageFromPrev)}</div>
  </c:if>
  <c:if test="${param.errorFromPrev != null}">
    <div class="error">エラー：${fn:escapeXml(param.errorFromPrev)}</div>
  </c:if>

  <c:if test="${requestScope.students != null}">
    <h2>学生一覧（${fn:escapeXml(requestScope.students.size())}件）</h2>
    <table>
      <thead>
        <tr>
          <td>学籍番号</td>
          <td>名前</td>
        </tr>
      </thead>
      <tbody>
        <%--
          【リストに含まれる各要素を出力する】
          `<c:forEach>`タグを使うと、`List`や`Map`の各アイテムを繰り返し同じ形式で出力することができる。
          ここでは、繰り返されるアイテムの変数名を`student`として（`var`属性で指定）、`student`の内容をテーブルの行（`tr`要素）に変換して出力している。
        --%>
        <c:forEach var="student" items="${requestScope.students}">
          <tr>
            <td>${fn:escapeXml(student.getId())}</td>
            <td>${fn:escapeXml(student.getName())}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>

  <h2>学生を登録する</h2>

  <%--
    【フォーム】
    ユーザからWebアプリケーションに情報を送信させるときは、HTTPリクエストを送信させる。
    `form`要素を用意することで、ユーザは`form`要素内の`input`要素を操作してリクエストの内容を編集したり、`button`要素をクリックしてHTTPリクエストを送信したりすることができるようになる。
    HTTPリクエストの送信先のURLは`form`要素の`action`属性で指定する。
    HTTPリクエストのメソッドは`form`要素の`method`属性で指定する。ユーザからWebアプリケーションに情報を送信させるときは、原則としてPOSTリクエストを送信させる。

    この例では、`servlet.students.CreateStudentServlet.doPost`クラスに処理させるために、
    `action`属性に`<コンテキストパス>/students/create`を、`method`属性に`post`を指定している。
  --%>
  <%--
    【コンテキストパスを取得する】
    サーブレットに処理をさせるためのリクエストの送信先のURLのパス部分はコンテキストパスで始まる。
    したがって、フォームの送信先やリンク先に使用するURLを絶対パスで指定する場合は、先頭にコンテキストパスを付ける必要がある。
    EL式の中では`pageContext.servletContext.getContextPath()`でコンテキストパスを得ることができる。
  --%>
  <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/students/create" method="post">
    <ul>
      <li>
        <%--
          【パラメータ・ユーザにパラメータの値を編集させるための`input`要素】
          `form`要素の内部に`input`要素を作成することで、`input`要素ごとに、対応する内容が「パラメータ」としてリクエストに含まれるようになる。
          パラメータはパラメータ名と値の組み合わせであり、パラメータ名には`name`属性に指定した値が使われ、パラメータの値には`input`要素に入力された値が使われる。
          `input`要素の初期値は`value`属性で指定できる。
          リクエストを処理するサーブレットでは、各パラメータの値を`HttpRequestServlet.getParameter`メソッドで得ることができる。
        --%>
        <label>学籍番号：<input type="text" name="id" value="${fn:escapeXml(param.lastInputId != null ? param.lastInputId : "")}"></label>
      </li>
      <li>
        <label>名前：<input type="text" name="name" value="${fn:escapeXml(param.lastInputName != null ? param.lastInputName : "")}"></label>
      </li>
    </ul>
    <%--
      【button要素・ユーザーにHTTPリクエストを送信させるためのボタン】
      `form`要素の内部に`type="submit"`属性を持つ`button`要素を作成することで、そのボタンが押された時にフォームに入力された内容が送信されるようになる。
    --%>
    <button type="submit">登録</button>
  </form>
</body>

</html>
