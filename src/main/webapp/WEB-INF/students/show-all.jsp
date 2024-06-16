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
  <%-- `<c:if>`タグを使うと、`test`属性で指定した条件式が真である場合のみ、内容を表示させることができる。 --%>
  <%-- `HttpServletRequest.setAttribute`メソッドで属性に割り当てたJavaオブジェクトの値は、EL式の中では`requestScope.<属性名>`として得ることができる。 --%>
  <c:if test="${requestScope.message != null}">
    <%-- Javaオブジェクトの値を出力に含める場合は、XSS対策として必ずHTMLの特殊文字をエスケープしておくこと。EL式の中では、`fn:escapeXml`関数に文字列を渡すと、エスケープ処理後の文字列を得られる。 --%>
    <div class="message"">メッセージ：${fn:escapeXml(requestScope.message)}</div>
  </c:if>
  <c:if test="${requestScope.error != null}">
    <div class="error">エラー：${fn:escapeXml(requestScope.error)}</div>
  </c:if>
  <%-- `HttpServletRequest.getParameter`メソッドで得られる値は、EL式の中では`param.<パラメータ名>`として得ることができる。 --%>
  <c:if test="${param.messageFromPrev != null}">
    <div class="message">メッセージ：${fn:escapeXml(param.messageFromPrev)}</div>
  </c:if>
  <c:if test="${param.errorFromPrev != null}">
    <div class="error">エラー：${fn:escapeXml(param.errorFromPrev)}</div>
  </c:if>
  <h2>学生一覧（${fn:escapeXml(requestScope.students != null ? requestScope.students.size() : "-")}件）</h2>
  <c:if test="${requestScope.students != null}">
    <table>
      <thead>
        <tr>
          <td>学籍番号</td>
          <td>名前</td>
        </tr>
      </thead>
      <tbody>
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
    サーブレットのURLには必ず先頭にコンテキストパスが付くので、フォームの送信先やリンク先に使用するURLを絶対パスで指定する場合は、先頭にコンテキストパスを付ける必要がある。
    EL式の中では`pageContext.servletContext.getContextPath()`でコンテキストパスを得ることができる。
  --%>
  <%-- 
    このフォームに入力した内容は`<コンテキストパス>/students/create`に対するPOSTリクエストとして送信される。
    リクエストは`servlet.students.CreateStudentServlet.doPost`メソッドによって処理される。
  --%>
  <form action="${fn:escapeXml(pageContext.servletContext.getContextPath())}/students/create" method="post">
    <ul>
      <li>
        <label>学籍番号：<input type="text" name="id" value="${fn:escapeXml(param.lastInputId != null ? param.lastInputId : '')}"></label>
        <label>名前：<input type="text" name="name" value="${fn:escapeXml(param.lastInputName != null ? param.lastInputName : '')}"></label>
      </li>
    </ul>
    <button type="submit">登録</button>
  </form>
</body>

</html>
