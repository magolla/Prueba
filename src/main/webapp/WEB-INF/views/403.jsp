<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>No autorizado</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>No tiene los permisos para acceder a esta pagina</h2>
		</c:when>
		<c:otherwise>
			<h2>Usuario : ${username} <br/>No tiene los permisos para acceder a esta pagina</h2>
		</c:otherwise>
	</c:choose>

</body>
</html>