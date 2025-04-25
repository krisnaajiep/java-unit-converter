<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Unit Converter</title>
    <link rel="stylesheet" href="css/style.css">
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
            <li><a href="index.jsp">Length</a></li>
            <li><a href="index.jsp">Weight</a></li>
            <li><a href="index.jsp">Temperature</a></li>
        </ul>
    </nav>
    <%--  Nav Section End  --%>
    <%--  Form Section Start  --%>
    <section class="form">
        <form action="" method="post">
            <div class="form-input">
                <label for="value">Enter the length to convert</label>
                <input type="number" name="value" id="value">
            </div>
            <div class="form-input">
                <label for="from">Unit to convert from</label>
                <select name="from" id="from">
                    <option value="m">Meter</option>
                </select>
            </div>
            <div class="form-input">
                <label for="to">Unit to convert to</label>
                <select name="to" id="to">
                    <option value="m">Meter</option>
                </select>
            </div>
            <button type="submit" name="convert">Convert</button>
        </form>
    </section>
    <%--  Form Section End  --%>
</body>
</html>