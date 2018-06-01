<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cache Details</title>
</head>
<body>
	<h2>Cache Servlet</h2>
	<ul>
		<c:forEach items="${cacheNames}" var="key">
			<li><span><c:out value="${key}" /></span><span> <a
					href='cacheAdmin?cacheName=<c:out value="${key}"/>'>Clear Cache</a></span><br />
				<br /></li>
		</c:forEach>
	</ul>

	<p>
		<a href="support">Go to Support Page</a>
	</p>
</body>
</html>