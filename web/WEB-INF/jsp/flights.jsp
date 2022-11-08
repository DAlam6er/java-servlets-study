<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><fmt:message key="page.flights.header"/></title>
</head>
<body>
    <h1><fmt:message key="page.flights.header"/>:</h1>
    <ul>
        <c:forEach var="flight" items="${requestScope.flights}">
            <li>
                <a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id}">
                        ${flight.description}</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
