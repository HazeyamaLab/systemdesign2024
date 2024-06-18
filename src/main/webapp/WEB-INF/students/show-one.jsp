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
  <title>学生の情報参照</title>
  <link rel="stylesheet" href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/index.css">
</head>

<body>
  <h1>学生の情報参照</h1>

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

  <c:if test="${requestScope.student != null}">
    <dl>
      <dt>学籍番号</dt>
      <dd>${fn:escapeXml(requestScope.student.getId())}</dd>
      <dt>氏名</dt>
      <dd>${fn:escapeXml(requestScope.student.getName())}</dd>
    </dl>
  </c:if>

  <nav>
    <p>
      <a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/students/show-all">学生一覧に戻る</a>
    </p>
  </nav>

</body>

</html>
