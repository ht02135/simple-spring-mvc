<html>
    <head>
        <title>Spring Security</title>
    </head>
    <body>
      <h3>You are not authorized to access this page.</h3>
      <form action="<%=request.getContextPath()%>/appLogout" method="POST">
        <input type="submit" value="Logout"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>		
      </form> 
    </body>
</html> 