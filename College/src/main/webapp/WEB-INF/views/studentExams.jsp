<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${empty exams}">
	<h4 class="text-danger text-center">There&nbsp;is&nbsp;no&nbsp;data&nbsp;for&nbsp;display!!!</h4>
</c:if>

<c:if test="${not empty exams}">
	<div class="table-responsive">
		<table class="table table-striped table-bordered">
			<tr>
				<th colspan="5">STUDENT&nbsp;:&nbsp;${exams[0].student.fullName}</th>
			</tr>
			<tr>
				<th>ID</th>
				<th>EXAM</th>
				<th>PROFESSOR</th>
				<th>DATE</th>
				<th>SCORE</th>
			</tr>
			<c:forEach items="${exams}" var="exam" varStatus="status">
				<tr>
					<td>${status.index + 1}</td>
					<td>${exam.subject.title}</td>
					<td>${exam.professor.fullName}</td>
					<td><tag:date date="${exam.date}"></tag:date></td>
					<td>${exam.score}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty student.exams}">
				<tr>
					<td colspan="5" class="text-center">No Data</td>
				</tr>
			</c:if>
			<tr>
				<th colspan="4">AVERAGE</th>
				<th><fmt:formatNumber minFractionDigits="2"
						maxFractionDigits="2" value="${exams[0].student.average}"></fmt:formatNumber>
				</th>
			</tr>
		</table>
	</div>
</c:if>


