<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message code="menu.title" text="default text" /> </a>
            <a class="navbar-brand navbar-right" href="?lang=fr">fr</a>
            <a class="navbar-brand navbar-right"href="?lang=en">en</a>
            <a class="navbar-brand navbar-right" href="logout"> <spring:message code="menu.logout"
                        text="default text" /></a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="alert alert-success">
                <spring:message code="addComputer.success" text="default text" />
                <br/>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/dashboard.js"></script>

</body>
</html>