<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        <li><a href="">Weight</a></li>
        <li><a href="">Temperature</a></li>
    </ul>
</nav>
<%--  Nav Section End  --%>
<% if (request.getParameter("convert") == null) { %>
<%--  Form Section Start  --%>
<section class="form">
    <form action="" method="post">
        <div class="form-input">
            <label for="value">Enter the length to convert</label>
            <input type="number" name="value" id="value" value="0" min="0">
        </div>
        <div class="form-input">
            <label for="from">Unit to convert from</label>
            <select name="from" id="from">
                <% Object[] lengthUnits = (Object[]) request.getAttribute("lengthUnits");
                    for (Object lengthUnit : lengthUnits) { %>
                <option value="<%= lengthUnit %>"><%= lengthUnit %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="form-input">
            <label for="to">Unit to convert to</label>
            <select name="to" id="to">
                <% for (Object lengthUnit : lengthUnits) { %>
                <option value="<%= lengthUnit %>"><%= lengthUnit %>
                </option>
                <% } %>
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
    <h2>205 = 25ft</h2>
    <h2><%= request.getAttribute("value").toString() %></h2>
    <form action=""  method="post">
        <button>Reset</button>
    </form>
</section>
<%--  Result Section End  --%>
<% } %>
</body>
</html>