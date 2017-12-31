<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="text-left" style="float: left;">
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="user" />
		<br>
		<h5>&nbsp;&nbsp;Logged: ${user.username}</h5>
	</sec:authorize>
</div>
<div class="text-right">
	<br>
	<sec:authorize access="isAuthenticated()">
		<sf:form action="${pageContext.request.contextPath}/logout"
			method="post">
			<button type="submit" class="btn btn-default">Log out</button>
		</sf:form>
	</sec:authorize>
	<br>
</div>
