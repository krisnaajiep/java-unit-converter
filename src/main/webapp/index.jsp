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
<% if (request.getParameter("convert") == null) { %>
<%--  Form Section Start  --%>
<section class="form">
    <form action="" method="post">
        <div class="form-input">
            <label for="value">Enter the length to convert</label>
            <input type="text" name="value" id="value" required>
        </div>
        <div class="form-input">
            <label for="from">Unit to convert from</label>
            <%--@elvariable id="lengthUnits" type="java.util.Set"--%>
            <select name="from" id="from" required>
                <c:forEach items="${lengthUnits}" var="lengthUnit">
                    <option value="${lengthUnit}">${lengthUnit}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-input">
            <label for="to">Unit to convert to</label>
            <select name="to" id="to" required>
                <c:forEach items="${lengthUnits}" var="lengthUnit">
                    <option value="${lengthUnit}">${lengthUnit}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" name="convert">Convert</button>
    </form>
</section>
<%--  Form Section End  --%>
<% } else { %>
<%--  Result Section Start  --%>
<section class="result">
    <h3>Result of your calculation</h3>
    <%--@elvariable id="result" type="java.util.Map"--%>
    <c:if test="${not empty result}">
        <h2><c:out value="${result.from}"/> = <c:out value="${result.to}"/></h2>
    </c:if>
    <form action=""  method="post">
        <button>Reset</button>
    </form>
</section>
<%--  Result Section End  --%>
<% } %>
</body>
</html>