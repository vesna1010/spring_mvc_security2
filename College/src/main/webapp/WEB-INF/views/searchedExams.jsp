<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:if test="${empty exams}">
	<h4 class="text-center text-danger">There&nbsp;is&nbsp;no&nbsp;exams!</h4>
</c:if>

<c:if test="${not empty exams}">
	<h3 class="text-center">Exams</h3>
	<br>
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr>
				<th colspan="5">DATE&nbsp;:&nbsp;<tag:date
						date="${exams[0].date}"></tag:date>
				</th>
			</tr>
			<tr>
				<th colspan="5">PROFESSOR&nbsp;:&nbsp;${exams[0].professor.fullName}</th>
			</tr>
			<tr>
				<th colspan="5">SUBJECT&nbsp;:&nbsp;${exams[0].subject.title}</th>
			</tr>
			<tr>
				<th></th>
				<th>STUDENT</th>
				<th>SCORE</th>
			</tr>
			<c:forEach items="${exams}" var="exam" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${exam.student.fullName}</td>
					<td>${exam.score}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</c:if>

