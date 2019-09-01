<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="text-center">Users</h3>
<br>

<c:if test="${empty users}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty users}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr class="success">
				<th>ID</th>
				<th>NAME</th>
				<th>EMAIL</th>
				<th>ROLES</th>
				<th>ENABLED</th>
				<th>MANAGE</th>
			</tr>
			<c:forEach items="${users}" var="user" varStatus="status">
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.email}</td>
					<td>${user.enabled}</td>
					<td><c:forEach var="role" items="${user.roles}">${role}<br>
						</c:forEach></td>
					<td><a class="btn btn-danger"
						href="<c:url value='/users/delete?userId=${user.id}' />"><span
							class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a> <a
						class="btn btn-default"
						href="<c:url value='/users/disable?userId=${user.id}' />"><span
							class="glyphicon glyphicon-remove"></span>&nbsp;Disable</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
