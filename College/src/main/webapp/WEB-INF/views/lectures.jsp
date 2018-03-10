<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="text-right">
	<a class="btn btn-default"
		href="<c:url value='/lectures/lectureForm?studyProgram=${studyProgram.id}'/>">&nbsp;Add
		New Lecture&nbsp;&nbsp;</a>
</div>

<h3 class="text-center">Lectures at ${studyProgram.title}</h3>
<br>

<c:if test="${empty studyProgram.lectures}">
	<h4 class="text-center text-danger">There are no lectures!</h4>
</c:if>

<c:if test="${not empty studyProgram.lectures}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr class="success">
				<th></th>
				<th>PROFESSOR</th>
				<th>SUBJECT</th>
				<th>HOURS</th>
				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>
			</tr>
			<c:forEach var="lecture" items="${studyProgram.lectures}"
				varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${lecture.professor.fullName}</td>
					<td>${lecture.subject.title}</td>
					<td>${lecture.hours}</td>
					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-primary"
							href="<c:url value='/lectures/edit/${lecture.id}/${studyProgram.id}'/>">
								<span class="glyphicon glyphicon-pencil"></span>&nbsp;Edit
						</a> <a class="btn btn-danger"
							href="<c:url value='/lectures/delete/${lecture.id}/${studyProgram.id}'/>">
								<span class="glyphicon glyphicon-remove"></span>&nbsp;Delete
						</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>

