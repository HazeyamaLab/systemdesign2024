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
  <p><a href="${fn:escapeXml(pageContext.servletContext.getContextPath())}/students/show-all">stuinfoを試す</a></p>
</body>

</html>
