<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">Professors</h3>
<br>

<c:if test="${empty professors}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty professors}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<tr class="success">
				<th>ID</th>
				<th>FULL&nbsp;NAME</th>
				<th>FATHER&nbsp;NAME</th>
				<th>DATE&nbsp;OF&nbsp;BIRTH</th>
				<th>EMAIL</th>
				<th>TELEPHONE</th>
				<th>GENDER</th>
				<th>ADDRESS</th>
				<th>IMAGE</th>
				<th>DATE&nbsp;OF&nbsp;EMPLOYMENT</th>
				<th>TITLE</th>
				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>
			</tr>

			<c:forEach items="${professors}" var="professor">
				<tr>
					<td>${professor.id}</td>
					<td>${professor.fullName}</td>
					<td>${professor.fatherName}</td>
					<td><tag:date date="${professor.dateOfBirth}"></tag:date></td>
					<td>${professor.email}</td>
					<td>${professor.telephone}</td>
					<td>${professor.gender}</td>
					<td>${professor.address}</td>
					<td><img
						src="data:image/jpeg;base64,${professor.imageToString()}"
						width="100" height="100" /></td>
					<td><tag:date date="${professor.dateOfEmployment}"></tag:date></td>
					<td>${professor.titleOfProfessor}</td>

					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-primary"
							href="<c:url value='/professors/edit?professorId=${professor.id}' />"><span
								class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a> <a
							class="btn btn-danger"
							href="<c:url value='/professors/delete?professorId=${professor.id}' />"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
