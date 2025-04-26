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
        <li><a href="?measurement=length"
                <c:if test="${param.measurement == null || param.measurement.equals('length')}">
                    class="active"
                </c:if>
        >Length</a></li>
        <li><a href="?measurement=weight"
                <c:if test="${param.measurement.equals('weight')}">
                    class="active"
                </c:if>
        >Weight</a></li>
        <li><a href="?measurement=temperature"
                <c:if test="${param.measurement.equals('temperature')}">
                    class="active"
                </c:if>
        >Temperature</a></li>
    </ul>
</nav>
<%--  Nav Section End  --%>
<%--@elvariable id="errors" type="java.lang.Map"--%>
<c:set value="${param.convert}" var="convert"/>
<c:choose>
    <c:when test="${convert == null || not empty errors}">
        <%--  Form Section Start  --%>
        <section class="form">
            <form action="" method="post">
                <div class="form-input">
                    <label for="value">Enter the <c:out value="${param.measurement}" default="length"/> to
                        convert</label>
                    <input type="text" name="value" id="value" value="<c:out value="${param.value}"/>">
                    <c:if test="${not empty errors['value']}">
                        <p class="error"><c:out value="${errors['value']}"/></p>
                    </c:if>
                </div>
                <div class="form-input">
                    <label for="from">Unit to convert from</label>
                        <%--@elvariable id="measurementUnits" type="java.util.Set"--%>
                    <select name="from" id="from">
                        <option value="">Select a unit</option>
                        <c:if test="${not empty measurementUnits}">
                            <c:forEach items="${measurementUnits}" var="lengthUnit">
                                <option value="<c:out value="${lengthUnit}"/>"
                                    <%--@elvariable id="from" type="java.lang.String"--%>
                                        <c:if test="${from != null && lengthUnit == from}">selected</c:if>>
                                    <c:out value="${lengthUnit}"/>
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <c:if test="${not empty errors['from']}">
                        <p class="error"><c:out value="${errors['from']}"/></p>
                    </c:if>
                </div>
                <div class="form-input">
                    <label for="to">Unit to convert to</label>
                    <select name="to" id="to">
                        <option value="">Select a unit</option>
                        <c:if test="${not empty measurementUnits}">
                            <c:forEach items="${measurementUnits}" var="lengthUnit">
                                <option value="<c:out value="${lengthUnit}"/>"
                                    <%--@elvariable id="to" type="java.lang.String"--%>
                                        <c:if test="${to != null && lengthUnit == to}">selected</c:if>>
                                    <c:out value="${lengthUnit}"/>
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <c:if test="${not empty errors['to']}">
                        <p class="error"><c:out value="${errors['to']}"/></p>
                    </c:if>
                </div>
                <button type="submit" name="convert">Convert</button>
            </form>
        </section>
        <%--  Form Section End  --%>
    </c:when>
    <c:otherwise>
        <%--  Result Section Start  --%>
        <section class="result">
            <h3>Result of your calculation</h3>
                <%--@elvariable id="result" type="java.util.Map"--%>
            <c:if test="${not empty result}">
                <h2><c:out value="${result.from}"/> = <c:out value="${result.to}"/></h2>
            </c:if>
            <form action="" method="post">
                <button>Reset</button>
            </form>
        </section>
        <%--  Result Section End  --%>
    </c:otherwise>
</c:choose>
</body>
</html>