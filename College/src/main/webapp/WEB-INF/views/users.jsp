<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="text-center">Users</h3>
<br>

<div class="table-responsive">
	<table class="table table-striped table-bordered">
		<tr class="success">
			<th>USERNAME</th>
			<th>EMAIL</th>
			<th>ROLES</th>
			<th>ENABLED</th>
			<th>MANAGE</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>${user.enabled}</td>
				<td><c:forEach items="${user.roles}" var="role">${role}<br>
					</c:forEach></td>
				<td><a class="btn btn-danger"
					href="<c:url value='/users/delete/${user.username}' />"><span
						class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a> <a
					class="btn btn-default"
					href="<c:url value='/users/disable/${user.username}' />"><span
						class="glyphicon glyphicon-remove"></span>&nbsp;Disable</a></td>
			</tr>
		</c:forEach>
	</table>
</div>

