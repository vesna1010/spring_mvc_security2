<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
	<div class="text-right">
		<a class="btn btn-default"
			href="<c:url value='/lectures/form?studyProgramId=${param.studyProgramId}'/>">&nbsp;Add&nbsp;New&nbsp;Lecture</a>
	</div>
</sec:authorize>

<h3 class="text-center">Lectures</h3>
<br>

<c:if test="${empty lectures}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty lectures}">
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

			<c:forEach var="lecture" items="${lectures}" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${lecture.professor.fullName}</td>
					<td>${lecture.subject.title}</td>
					<td>${lecture.hours}</td>

					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-danger"
							href="<c:url value='/lectures/delete?professorId=${lecture.professor.id}&subjectId=${lecture.subject.id}&studyProgramId=${param.studyProgramId}'/>">
								<span class="glyphicon glyphicon-remove"></span>&nbsp;Delete
						</a></td>
					</sec:authorize>

				</tr>
			</c:forEach>
		</table>
	</div>

</c:if>
