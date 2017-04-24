<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <a class="navbar-brand" href="dashboard"> Application -
                Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">id:
                        ${computerDto.id}</div>
                    <h1>Edit Computer</h1>

                    <form action="editcomputer" method="POST"
                        name="computer">
                        <input type="hidden" value="${computerDto.id}"
                            id="id" name="id" />
                        <!-- TODO: Change this value with the computer id -->
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer
                                    name</label> <input type="text"
                                    class="form-control"
                                    id="computerName"
                                    name="computerName"
                                    placeholder="Computer name"
                                    value="${computerDto.name}"
                                    minlength="2" required>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced
                                    date</label> <input type="date"
                                    class="form-control" id="introduced"
                                    name="introduced"
                                    placeholder="Introduced date"
                                    value="${computerDto.introduced}"
                                    onchange="introD();" max="">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued
                                    date</label> <input type="date"
                                    class="form-control"
                                    id="discontinued"
                                    name="discontinued"
                                    placeholder="Discontinued date"
                                    value="${computerDto.discontinued}"
                                    onchange="discD();"
                                    min="${computerDto.introduced}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label> <select
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
                            <input type="submit" value="Edit"
                                class="btn btn-primary"> or <a
                                href="dashboard" class="btn btn-default">Cancel</a>
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