<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@ include file="header.jsp"%>
    <title><fmt:message key="page.login.header"/></title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="emailId"><fmt:message key="page.login.email"/>:
            <input type="text" name="email" id="emailId" value="${param.email}" required>
        </label><br>
        <label for="passwordId"><fmt:message key="page.login.password"/>:
            <input type="password" name="password" id="passwordId" required>
        </label><br>
        <button type="submit"><fmt:message key="page.login.submit.button"/></button>
        <a href="${pageContext.request.contextPath}/registration">
            <button type="button"><fmt:message key="page.login.register.button"/></button>
        </a>
        <c:if test="${param.error != null}">
            <div style="color: red">
                <%--<span>Email or password is not correct</span>--%>
                <span><fmt:message key="page.login.error"/></span>
            </div>
        </c:if>
    </form>
</body>
</html>
