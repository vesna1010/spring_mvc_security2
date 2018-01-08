<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${empty student.exams}">
	<h4 class="text-center text-danger">Student ${student.fullName}
		has no passed exams!</h4>
</c:if>

<c:if test="${not empty student.exams}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr>
				<th colspan="5">STUDENT : ${student.fullName}</th>
			</tr>
			<tr>
				<th>ID</th>
				<th>EXAM</th>
				<th>PROFESSOR</th>
				<th>DATE</th>
				<th>SCORE</th>
			</tr>
			<c:forEach items="${student.exams}" var="exam">
				<tr>
					<td>${exam.id}</td>
					<td>${exam.subject.title}</td>
					<td>${exam.professor.fullName}</td>
					<td><tag:date date="${exam.date}"></tag:date></td>
					<td>${exam.score}</td>
				</tr>
			</c:forEach>
			<tr>
				<th colspan="4">AVERAGE</th>
				<th><fmt:formatNumber minFractionDigits="2"
						maxFractionDigits="2" value="${student.average}"></fmt:formatNumber>
				</th>
			</tr>
		</table>
	</div>
</c:if>



