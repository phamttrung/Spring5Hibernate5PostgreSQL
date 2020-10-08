<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/resources/css/cdnjs-cloudflare-font-awesome.css' />" rel="stylesheet"></link>
</head>
<body style="overflow:auto">
    <div id="mainWrapper" align="center">
        <div align="center">
            <td align="left" width="50%"><img src="<c:url value='/resources/logos/login_logo.png'/>" width="300" height="180"/></td>
            <br><br>
            <p class="height">
                <font size="6" face="arial" color="#01569B"><strong>Application Name</strong> </font> 
            </p>
        </div>
        <div class="login-container">
            <div class="login-card">
                <div class="login-form">
                    <c:url var="loginUrl" value="/login.home" />
                    <form action="${loginUrl}" method="post" class="form-horizontal">
                        <c:if test="${param.error != null}">
                            <div class="alert alert-danger">
                                <p>Invalid username and password.</p>
                            </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="alert alert-success">
                                <p>You have been logged out successfully.</p>
                            </div>
                        </c:if>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="username">Username</label>
                            <input type="text" class="form-control" id="username" name="username" placeholder="Enter Username" required>
                        </div>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="password">Password&nbsp;</label> 
                            <input type="password" class="form-control" id="password" name="password" placeholder="Enter Password" required>
                        </div>
                        <!--input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /-->
                        <br>
                        <div class="form-actions">
                            <input type="submit" class="btn btn-block btn-primary btn-default" value="Log in">
                        </div>
                    </form>
                </div>
            </div>
        </div>
              
    </div>
</body>
</html>