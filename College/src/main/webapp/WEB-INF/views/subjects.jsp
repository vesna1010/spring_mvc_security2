<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>


<div class="row">
<div class="col-sm-12">

<c:if test="${empty subjects}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty subjects}">
<h3 class="text-center">Subjects
<c:if test="${studyProgram!=null}"> at ${studyProgram.title}
</c:if></h3><br>

<div class="table-responsive">
<table class="table table-striped table-bordered">
<tr class="success">
<th>ID</th>
<th>TITLE</th>
<th>PROFESSORS</th>

<c:if test="${studyProgram==null}">
<th>STUDY PROGRAM</th>
<sec:authorize access="hasRole('USER')">
<th>MANAGE</th>
</sec:authorize>
</c:if>

</tr>

<c:forEach items="${subjects}" var="subject">
<tr>
<td>${subject.id}</td>
<td>${subject.title}</td>
<td>
<c:forEach items="${subject.professors}" var="professor">
${professor.fullName}<br>
</c:forEach>
</td>

<c:if test="${studyProgram==null}">
<td>${subject.studyProgram.title}</td>
<sec:authorize access="hasRole('USER')">
<td>
<a class="btn btn-primary" href="<c:url value='/subjects/find/${subject.id}' />"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
<a class="btn btn-danger" href="<c:url value='/subjects/delete/${subject.id}' />"><span class="glyphicon glyphicon-remove"></span> Delete</a>
</td>
</sec:authorize>
</c:if>

</tr>
</c:forEach>
</table>
</div>

</c:if>

</div>
</div>