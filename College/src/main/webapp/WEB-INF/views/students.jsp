<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">Students</h3>
<br>

<c:if test="${empty students}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty students}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<tr class="active">
				<th>ID</th>
				<th>FULL&nbsp;NAME</th>
				<th>FATHER&nbsp;NAME</th>
				<th>DATE&nbsp;OF&nbsp;BIRTH</th>
				<th>EMAIL</th>
				<th>TELEPHONE</th>
				<th>GENDER</th>
				<th>ADDRESS</th>
				<th>IMAGE</th>
				<th>DATE&nbsp;OF&nbsp;ENTRY</th>
				<th>YEAR&nbsp;OF&nbsp;STUDY</th>
				<th>AVERAGE</th>
				<th>STUDY&nbsp;PROGRAMS</th>
				<th>MANAGE</th>
			</tr>

			<c:forEach var="student" items="${students}">
				<tr>
					<td>${student.id}</td>
					<td>${student.fullName}</td>
					<td>${student.fatherName}</td>
					<td><tag:date date="${student.dateOfBirth}"></tag:date></td>
					<td>${student.email}</td>
					<td>${student.telephone}</td>
					<td>${student.gender}</td>
					<td>${student.address}</td>
					<td><img
						src="data:image/jpeg;base64,${student.imageToString()}"
						width="100" height="100" /></td>
					<td><tag:date date="${student.dateOfEntry}"></tag:date></td>
					<td>${student.yearOfStudy}</td>
					<td>${student.average}</td>
					<td>${student.studyProgram.title}</td>

					<td><sec:authorize access="hasAnyRole('USER', 'ADMIN')">
							<a class="btn btn-primary"
								href="<c:url value='/students/edit?studentId=${student.id}' />"><span
								class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a>
							<a class="btn btn-danger"
								href="<c:url value='/students/delete?studentId=${student.id}' />"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a>
						</sec:authorize> <a class="btn btn-default"
						href="<c:url value='/exams/student?studentId=${student.id}' />">&nbsp;Exams</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
