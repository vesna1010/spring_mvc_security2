<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row">
<div class="col-sm-12">
<c:if test="${student==null}">
<h4 class="text-center text-danger">Student Not Found!</h4>
</c:if>

<c:if test="${student!=null}">

<c:if test="${empty student.exams}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty student.exams}">
<h3 class="text-center">All Exams</h3><br>
<div class="table-responsive">
<table class="table table-striped table-bordered">
<tr>
<th colspan="5">STUDENT : ${student.fullName} </th>
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
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${exam.date}"/></td>
<td>${exam.score}</td>
</tr>
</c:forEach>
<tr>
<th colspan="4">AVERAGE</th>
<th><fmt:formatNumber minFractionDigits="2" maxFractionDigits="2"  value="${student.average}"></fmt:formatNumber>
</th>
</tr>
</table>
</div>

</c:if>
</c:if>

</div>
</div>