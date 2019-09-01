<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">Subjects</h3>
<br>

<c:if test="${empty subjects}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty subjects}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">

			<tr class="success">
				<th>ID</th>
				<th>TITLE</th>
				<th>STUDY PROGRAM</th>
				<th>PROFESSORS</th>
				<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
					<th>MANAGE</th>
				</sec:authorize>
			</tr>

			<c:forEach items="${subjects}" var="subject">
				<tr>
					<td>${subject.id}</td>
					<td>${subject.title}</td>
					<td>${subject.studyProgram.title}</td>
					<td><c:forEach var="professor" items="${subject.professors}">${professor.fullName}<br>
						</c:forEach></td>
						
					<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
						<td><a class="btn btn-primary"
							href="<c:url value='/subjects/edit?subjectId=${subject.id}' />"><span
								class="glyphicon glyphicon-pencil"></span>&nbsp;Edit</a> <a
							class="btn btn-danger"
							href="<c:url value='/subjects/delete?subjectId=${subject.id}' />"><span
								class="glyphicon glyphicon-remove"></span>&nbsp;Delete</a></td>
					</sec:authorize>
					
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
