<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:if test="${empty exams}">
	<h4 class="text-center text-danger">There is no exams!</h4>
</c:if>

<c:if test="${not empty exams}">
	<h3 class="text-center">All Exams</h3>
	<br>
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr>
				<th colspan="5">DATE : <tag:date date="${exams.first().date}"></tag:date>
				</th>
			</tr>
			<tr>
				<th colspan="5">PROFESSOR : ${exams.first().professor.fullName}</th>
			</tr>
			<tr>
				<th colspan="5">SUBJECT : ${exams.first().subject.title}</th>
			</tr> 
			<tr>
				<th>ID</th>
				<th>STUDENT</th>
				<th>SCORE</th>
				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>
			</tr>
			<c:forEach items="${exams}" var="exam">
				<tr>
					<td>${exam.id}</td>
					<td>${exam.student.fullName}</td>
					<td>${exam.score}</td>
					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-primary"
							href="<c:url value='/exams/edit/${exam.id}' />"><span
								class="glyphicon glyphicon-pencil"></span> Edit</a> <a
							class="btn btn-danger"
							href="<c:url value='/exams/delete/${exam.id}' />"><span
								class="glyphicon glyphicon-remove"></span> Delete</a></td>
					</sec:authorize>
				</tr>
			</c:forEach>
		</table>
	</div>

</c:if>
