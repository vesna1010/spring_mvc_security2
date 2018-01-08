<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">${title}</h3>
<br>

<c:if test="${empty professors}">
	<h4 class="text-center text-danger">There are no professors!</h4>
</c:if>

<c:if test="${not empty professors}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr class="success">
				<th>ID</th>
				<th>DATE OF EMPLOYMENT</th>
				<th>FULL NAME</th>
				<th>FATHER NAME</th>
				<th>GENDER</th>
				<th>DATE OF BIRTH</th>
				<th>EMAIL</th>
				<th>TELEPHONE</th>
				<th>ADDRESS</th>
				<th>TITLE</th>
				<th>IMAGE</th>
				<th>SUBJECTS</th>
				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>
			</tr>
			<c:forEach items="${professors}" var="professor">
				<tr>
					<td>${professor.id}</td>
					<td><tag:date date="${professor.dateOfEmployment}"></tag:date></td>
					<td>${professor.fullName}</td>
					<td>${professor.fatherName}</td>
					<td>${professor.gender}</td>
					<td><tag:date date="${professor.dateOfBirth}"></tag:date></td>
					<td>${professor.email}</td>
					<td>${professor.telephone}</td>
					<td>${professor.address}</td>
					<td>${professor.titleOfProfessor}</td>
					<td><img src="data:image/jpeg;base64,${professor.showImage()}"
						width="100" height="100" /></td>
					<td><c:forEach items="${professor.subjects}" var="subject">${subject.title}<br>
						</c:forEach></td>
					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-primary"
							href="<c:url value='/professors/edit/${professor.id}' />"><span
								class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a> <a
							class="btn btn-danger"
							href="<c:url value='/professors/delete/${professor.id}' />"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>

