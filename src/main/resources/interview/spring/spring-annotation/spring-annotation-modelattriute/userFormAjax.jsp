<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Form AJAX</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>${commonMessage}</h2>

<form id="userForm">
    Name: <input type="text" name="name" value="${user.name}"/><br/>
    Email: <input type="text" name="email" value="${user.email}"/><br/>
    <button type="submit">Save via AJAX</button>
</form>

<hr/>
<div id="result"></div>

<script>
/*
1>$("#userForm").on("submit", ...) attaches a handler to the form’s submit event.
2>Inside that handler, this refers to the DOM element that triggered the event → in this case, 
the <form> element with id="userForm".
3>$(this) wraps that DOM element into a jQuery object, so you can call jQuery methods on it.
*/
$(document).ready(function () {
    $("#userForm").on("submit", function (e) {
        e.preventDefault();

        $.ajax({
            url: "${pageContext.request.contextPath}/user/save",
            type: "POST",
            data: $(this).serialize(),
            success: function (data) {
                $("#result").html(
                    "<h3>Saved User:</h3>" +
                    "Name: " + data.name + "<br/>" +
                    "Email: " + data.email
                );
            },
            error: function (xhr) {
                $("#result").html("<p style='color:red;'>Error saving user!</p>");
            }
        });
    });
});
</script>

</body>
</html>
