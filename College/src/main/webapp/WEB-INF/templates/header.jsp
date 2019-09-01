<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="text-left" style="float: left;">
	<sec:authorize access="isAuthenticated()">
		<sec:authentication var="user" property="principal" />
		<br>
		<h5>&nbsp;&nbsp;Logged&nbsp;:&nbsp;${user.username}</h5>
	</sec:authorize>
</div>

<sec:authorize access="isAuthenticated()">
	<br />
	<div class="text-right">
		<sf:form action="${pageContext.request.contextPath}/logout"
			method="post">
			<button type="submit" class="btn btn-default">Log&nbsp;out</button>
		</sf:form>
	</div>
</sec:authorize>
