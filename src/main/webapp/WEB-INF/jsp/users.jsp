<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users</title>
</head>
<body>
    <h1 align="center">Users</h1>
    <br/>
    <table border="1" cellpadding="10">
        <tr>
            <th>Name</th>
        </tr>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user}</td>
        </tr>    
        </c:forEach>
    </table>
</body>
</html>