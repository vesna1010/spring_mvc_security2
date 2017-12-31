<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-sm-4 col-sm-offset-4">
		<br>
		<br>
		<h3>Log in</h3>

		<c:if test="${param.error}">
			<b class="text-danger">Check username and password</b>
			<br>
			<br>
		</c:if>

		<form action="${pageContext.request.contextPath}/login" method="post">
			<div class="form-group">
				<input name="username" class="form-control" placeholder="Username">
			</div>

			<div class="form-group">
				<input type="password" name="password" class="form-control"
					placeholder="Password">
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<button type="submit" class="btn btn-primary btn-block">Log
				in</button>
		</form>

	</div>
</div>
