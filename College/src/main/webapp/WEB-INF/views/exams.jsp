<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
	<div class="text-right">
		<a class="btn btn-default"
			href="<c:url value='/exams/form?studyProgramId=${param.studyProgramId}'/>">&nbsp;Add&nbsp;New&nbsp;Exam</a>
	</div>
</sec:authorize>

<h3 class="text-center">Exams</h3>
<br>

<c:if test="${empty exams}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty exams}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<tr class="success">
				<th></th>
				<th>STUDENT</th>
				<th>SUBJECT</th>
				<th>PROFESSOR</th>
				<th>DATE</th>
				<th>SCORE</th>

				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>

			</tr>

			<c:forEach items="${exams}" var="exam" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${exam.student.fullName}</td>
					<td>${exam.subject.title}</td>
					<td>${exam.professor.fullName}</td>
					<td><tag:date date="${exam.date}"></tag:date></td>
					<td>${exam.score}</td>

					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-danger"
							href="<c:url value='/exams/delete?studentId=${exam.student.id}&subjectId=${exam.subject.id}&studyProgramId=${param.studyProgramId}'/>">
								<span class="glyphicon glyphicon-remove"></span>&nbsp;Delete
						</a></td>
					</sec:authorize>

				</tr>
			</c:forEach>
		</table>
	</div>

</c:if>
