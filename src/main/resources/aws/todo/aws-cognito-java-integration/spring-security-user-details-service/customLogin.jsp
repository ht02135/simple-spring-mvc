<html>
    <head>
        <title>Spring Security Login</title>
    </head>
    <body>
	<h3>Spring Security Login</h3>
	<font color="red">
		 ${SPRING_SECURITY_LAST_EXCEPTION.message}
	</font>
	<form action="<%=request.getContextPath()%>/appLogin" method="POST">
	   <table border='1' cellspacing='0' cellpadding='10'>
	   <tr><td>Username:</td> <td><input type="text" name="username"/></td></tr>
	   <tr><td>Password:</td> <td><input type="password" name="password"/></td></tr>
	   <tr>
		 <td colspan='2' align='center'><input type="submit" value="Login"/>
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>			
		 </td>
	   </tr>
	   </table>
	</form>
    </body>
</html> 