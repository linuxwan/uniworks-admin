<!DOCTYPE html>
<html>
<body>
	<h1>HTTP Status 403 - Access is denied</h1>

	<c:choose>
		<c:when test="${empty username}">
			<h2>You do not have permission to access this page!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username : ${username} <br/>You do not have permission to access this page!</h2>
			<c:out value="${exception.message}"/>
		</c:otherwise>
	</c:choose>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</body>
</html>