<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head><title>User Form</title></head>
<body>
    <h2>${commonMessage}</h2>
    <form:form modelAttribute="user" action="save" method="post">
        Name: <form:input path="name"/><br/>
        Email: <form:input path="email"/><br/>
        <input type="submit" value="Save"/>
    </form:form>
</body>
</html>
