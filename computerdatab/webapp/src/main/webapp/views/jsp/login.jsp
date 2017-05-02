<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
    media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
    media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message
                    code="menu.title" text="default text" />
            </a> <a class="navbar-brand navbar-right" href="?lang=fr">fr</a>
            <a class="navbar-brand navbar-right" href="?lang=en">en</a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <c:if test="${param.error ne null}">
                <div class="alert alert-danger">
                    <spring:message code="login.error"
                        text="default text" />
                </div>
            </c:if>
            <c:if test="${param.logout ne null}">
                <div class="alert alert-success">
                    <spring:message code="login.logout"
                        text="default text" />
                </div>
            </c:if>


            <form th:action="@{/login}" method="post">
                <div>
                    <label> <spring:message
                            code="login.username" text="default text" />
                        : <input type="text" name="username" />
                    </label>
                </div>
                <input type="hidden" name="${_csrf.parameterName}"
                    value="${_csrf.token}" />
                <div>
                    <label> <spring:message
                            code="login.password" text="default text" />
                        : <input type="password" name="password" />
                    </label>
                </div>
                <div>
                    <input type="submit" value="Sign In" />
                </div>
            </form>
        </div>
    </section>

    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/dashboard.js"></script>

</body>
</html>