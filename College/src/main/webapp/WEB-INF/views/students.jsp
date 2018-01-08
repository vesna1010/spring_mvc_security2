<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">${title}</h3>
<br>

<c:if test="${empty students}">
	<h4 class="text-center text-danger">There are no students!</h4>
</c:if>

<c:if test="${not empty students}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr class="active">
				<th>ID</th>
				<th>DATE OF ENTRY</th>
				<th>FULL NAME</th>
				<th>FATHER NAME</th>
				<th>GENDER</th>
				<th>DATE OF BIRTH</th>
				<th>EMAIL</th>
				<th>TELEPHONE</th>
				<th>ADDRESS</th>
				<th>YEAR OF STUDY</th>
				<th>STUDY PROGRAMS</th>
				<th>IMAGE</th>
				<th>MANAGE</th>
			</tr>
			<c:forEach items="${students}" var="student">
				<tr>
					<td>${student.id}</td>
					<td><tag:date date="${student.dateOfEntry}"></tag:date></td>
					<td>${student.fullName}</td>
					<td>${student.fatherName}</td>
					<td>${student.gender}</td>
					<td><tag:date date="${student.dateOfBirth}"></tag:date></td>
					<td>${student.email}</td>
					<td>${student.telephone}</td>
					<td>${student.address}</td>
					<td>${student.yearOfStudy}</td>
					<td>${student.studyProgram.title}</td>
					<td><img src="data:image/jpeg;base64,${student.showImage()}"
						width="100" height="100" /></td>
					<td><sec:authorize access="hasAnyRole('USER', 'ADMIN')">
							<a class="btn btn-primary"
								href="<c:url value='/students/edit/${student.id}' />"><span
								class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a>
							<a class="btn btn-danger"
								href="<c:url value='/students/delete/${student.id}' />"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a>
						</sec:authorize> <a class="btn btn-default"
						href="<c:url value='/students/exams/${student.id}' />">&nbsp;Exams</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>

