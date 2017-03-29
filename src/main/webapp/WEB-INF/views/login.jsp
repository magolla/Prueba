<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	

		<form:form action="login" method="post" modelAttribute="loginObject">
		
		
		<form:label path="userName">Usuario</form:label>
		<form:input id="userName" name="userName" path="userName"/>
		
		<form:label path="password">Password</form:label>
		<form:input type="password" id="password" name="password" path="password"/>
		
		<input type="submit" value="Submit">
		</form:form>
</body>
</html>