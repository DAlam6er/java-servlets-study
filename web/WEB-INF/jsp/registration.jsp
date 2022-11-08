<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <%@include file="header.jsp"%>
    <title><fmt:message key="page.registration.header"/></title>
</head>
<body>
<%--<img src="http://webmeup.com/upload/blog/lead-image-105.png" alt="User image" width="69" height="72">--%>
<img src="${pageContext.request.contextPath}/images/users/photo_2021-01-01_14-21-25.jpg"
     alt="User image" width="64" height="64">
<form action="${pageContext.request.contextPath}/registration"
      method="post" enctype="multipart/form-data">
    <label for="nameId"><fmt:message key="page.registration.name"/>:
        <input type="text" name="name" id="nameId" required>
    </label><br>
    <label for="birthdayId"><fmt:message key="page.registration.birthday"/>:
        <input type="date" name="birthday" id="birthdayId" required>
    </label><br>
    <label for="emailId"><fmt:message key="page.registration.email"/>:
        <input type="text" name="email" id="emailId" required>
    </label><br>
    <label for="imageId"><fmt:message key="page.registration.image"/>:
        <input type="file" name="image" id="imageId" required>
    </label><br>
    <label for="passwordId"><fmt:message key="page.registration.password"/>:
        <input type="password" name="password" id="passwordId" required>
    </label><br>
    <%--    ?role=USER--%>
    <label for="roleId">
        <select name="role" id="roleId">
            <%--
            USER без кавычек - это то, что отображается пользователю
            Статические роли
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
            --%>
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select>
    </label><br>
    <%--
    <label for="genderMaleId">
        <input type="radio" name="gender" value="MALE" id="genderMaleId"> Male
    </label><br>
    <label for="genderFemaleId">
        <input type="radio" name="gender" value="MALE" id="genderFemaleId"> Female
    </label><br>
    --%>
    <c:forEach var="gender" items="${requestScope.genders}">
        <label for="${gender}Id">
            <input type="radio" name="gender" value="${gender}" id="${gender}Id" required>${gender}
        </label><br>
    </c:forEach>
    <button type="submit"><fmt:message key="page.registration.submit.button"/></button>
    <c:if test="${not empty requestScope.errors}">
        <div style="color:red">
            <c:forEach var="error" items="${requestScope.errors}">
                <span>${error.message}</span><br>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
