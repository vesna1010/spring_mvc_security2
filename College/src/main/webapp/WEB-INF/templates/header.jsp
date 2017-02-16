<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="text-left" style="float:left;">
<sec:authorize  access="isAuthenticated()">
<sec:authentication property="principal" var="user"/>
<br>
<h5>&nbsp;&nbsp;Logged: ${user.username}</h5>
</sec:authorize>
</div>
<div class="text-right">
<br>
<sec:authorize  access="isAuthenticated()">
<c:url value='/logout' context='/College' var="action"/>
<form action="${action}" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<button type="submit" class="btn btn-default">Logout</button>&nbsp;&nbsp;
</form>
</sec:authorize>
<br>
</div>
