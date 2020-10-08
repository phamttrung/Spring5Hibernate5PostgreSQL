<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
    <div class="generic-container" style="overflow: scroll; height : calc(100% - 4px); width: calc(100% - 4px)">
        <%@include file="authheader.jsp" %> 
        <sec:authorize access="hasRole('ADMIN')">
            <table width="100%" border="0" cellpadding="5" cellspacing="0">
                <tr>
                    <td align="left" width="50%" height="30px"> 
                        <a href="<c:url value='/newuser' />"><b>&nbsp; Add new user</b></a>
                    </td>
                    <td align="right" width="50%">
                        <a href="<c:url value='/' />"><b>Go back to home page &nbsp;</b></a>
                    </td>
                </tr>
            </table>

            <div class="panel panel-default">
                <!-- Default panel contents -->
                <!--div class="panel-heading"><span class="lead">List of Users </span></div-->
                <table class="table table-hover">
                    <thead>
                          <tr>
                            <th>Firstname</th>
                            <th>Lastname</th>
                            <th>Email</th>
                            <th>Username</th>
                            <th>Organization</th>
                            <sec:authorize access="hasRole('ADMIN')"><th width="100"></th></sec:authorize>
                            <sec:authorize access="hasRole('ADMIN')"><th width="100"></th></sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.email}</td>
                            <td>${user.username}</td>
                            <td>${user.organization}</td>
                            <sec:authorize access="hasRole('ADMIN')">
                                <td><a href="<c:url value='/edit-user-${user.username}' />" class="btn btn-success custom-width">edit</a></td>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ADMIN')">
                                <td><a href="<c:url value='/delete-user-${user.username}' />" class="btn btn-danger custom-width">delete</a></td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
         </sec:authorize>
    </div>
</body>
</html>