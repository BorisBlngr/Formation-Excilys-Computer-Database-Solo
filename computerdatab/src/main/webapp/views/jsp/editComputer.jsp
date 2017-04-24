<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">id:
                        ${computerDto.id}</div>
                    <h1>
                        <spring:message code="editComputer.title"
                            text="default text" />
                    </h1>

                    <form action="editcomputer" method="POST"
                        name="computer">
                        <input type="hidden" value="${computerDto.id}"
                            id="id" name="id" />
                        <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message
                                        code="label.computerName"
                                        text="default text" /></label> <input
                                    type="text" class="form-control"
                                    id="computerName"
                                    name="computerName"
                                    placeholder="<spring:message
                                        code="label.computerName"
                                        text="default text" />"
                                    value="${computerDto.name}"
                                    minlength="2" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message
                                        code="label.introduced"
                                        text="default text" /></label> <input
                                    type="date" class="form-control"
                                    id="introduced" name="introduced"
                                    placeholder="<spring:message
                                        code="label.introduced"
                                        text="default text" />"
                                    value="${computerDto.introduced}"
                                    onchange="introD();" max="">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message
                                        code="label.discontinued"
                                        text="default text" /></label> <input type="date"
                                    class="form-control"
                                    id="discontinued"
                                    name="discontinued"
                                    placeholder="<spring:message
                                        code="label.discontinued"
                                        text="default text" />"
                                    value="${computerDto.discontinued}"
                                    onchange="discD();"
                                    min="${computerDto.introduced}">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message
                                        code="label.company"
                                        text="default text" /></label> <select
                                    class="form-control" id="companyId"
                                    name="companyId">
                                    <option value="0">--</option>
                                    <c:forEach items="${companyDtoList}"
                                        var="companyDtoList">
                                        <option
                                            value="${companyDtoList.id}"
                                            <c:if test="${companyDtoList.id == computerDto.company.id}">selected="selected"</c:if>>${companyDtoList.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message
                                        code="editComputer.edit"
                                        text="default text" />"
                                class="btn btn-primary"> <spring:message
                                        code="editComputer.or"
                                        text="default text" /> <a
                                href="dashboard" class="btn btn-default"><spring:message
                                        code="editComputer.cancel"
                                        text="default text" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="resources/js/addEditValidate.js"></script>
</body>
</html>