<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Spring Security Example</title>
    </head>
    <body>
      <h3>Hello <c:out value="${name}"/></h3>
      <form action="<%=request.getContextPath()%>/appLogout" method="POST">
        <input type="submit" value="Logout"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>		
      </form>      
    </body>
</html> 