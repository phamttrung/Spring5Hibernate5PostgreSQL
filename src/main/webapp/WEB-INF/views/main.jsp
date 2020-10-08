<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Main Page</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
    <div class="generic-container" style="overflow: scroll; height : calc(100% - 4px); width: calc(100% - 4px)">
        <%@include file="authheader.jsp" %> 
        <sec:authorize access="hasRole('ADMIN') or hasRole('USER')">
            Main Web Page
        </sec:authorize>
    </div>
</body>
</html>