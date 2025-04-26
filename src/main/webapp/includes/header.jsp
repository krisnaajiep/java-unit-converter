<%--
  Created by IntelliJ IDEA.
  User: krisna
  Date: 26/04/25
  Time: 11.46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Unit Converter</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%--  Header Section Start  --%>
<header>
  <h1>Unit Converter</h1>
</header>
<%--  Header Section End  --%>
<%--  Nav Section Start  --%>
<nav>
  <ul>
    <li><a href="length">Length</a></li>
    <li><a href="weight">Weight</a></li>
    <li><a href="temperature">Temperature</a></li>
  </ul>
</nav>
<%--  Nav Section End  --%>
