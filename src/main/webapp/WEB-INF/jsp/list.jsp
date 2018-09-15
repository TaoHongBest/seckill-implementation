<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Import jstl --%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seckill List</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<%-- Page Display --%>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>Seckill List</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Stock</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Create Time</th>
                    <th>Details</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sk" items="${list}">
                    <tr>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td>
                            <fmt:formatDate value="${sk.startTime}" pattern="dd-MM-yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.endTime}" pattern="dd-MM-yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.createTime}" pattern="dd-MM-yyyy HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>

</div>
</body>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</html>