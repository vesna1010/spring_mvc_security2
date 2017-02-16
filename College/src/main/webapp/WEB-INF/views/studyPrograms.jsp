<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${empty studyPrograms}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty studyPrograms}">
<h3 class="text-center">StudyPrograms
<c:if test="${department!=null}"> at ${department.title} </c:if></h3><br>

<div class="table-responsive">
<table class="table table-striped table-bordered">
<tr class="success">
<th>ID</th>
<th>NAME</th>
<th>DATE OF CREATION</th>
<th>DURATION OF STUDY</th>
<th>ECTS</th>

<c:if test="${department==null}">
<th>DEPARTMENT</th>
<th>MANAGE</th>
</c:if>
</tr>

<c:forEach items="${studyPrograms}" var="studyProgram">
<tr>
<td>${studyProgram.id}</td>
<td>${studyProgram.title}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${studyProgram.dateOfCreation}"/></td>
<td>${studyProgram.durationOfStudy}</td>
<td>${studyProgram.ects}</td>

<c:if test="${department==null}">
<td>${studyProgram.department.title}</td>
<td>

<sec:authorize access="hasRole('USER')">
<a class="btn btn-primary" href="<c:url value='/studyPrograms/find/${studyProgram.id}' />"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
<a class="btn btn-danger" href="<c:url value='/studyPrograms/delete/${studyProgram.id}' />"><span class="glyphicon glyphicon-remove"></span> Delete</a>
<a class="btn btn-default" href="<c:url value='/exams/save/?id=${studyProgram.id}' />">Add Exams</a>
</sec:authorize>

<a class="btn btn-default" href="<c:url value='/students/?id=${studyProgram.id}' />">Students</a>
<a class="btn btn-default" href="<c:url value='/subjects/?id=${studyProgram.id}' />">Subjects</a>
<a class="btn btn-default" href="<c:url value='/professors/?id=${studyProgram.id}' />">Professors</a>
</td>
</c:if>

</tr>
</c:forEach>
</table>
</div>
</c:if>

</div>
</div>