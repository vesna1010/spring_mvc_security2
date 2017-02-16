<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${empty departments}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty departments}">
<h3 class="text-center">All Departments</h3><br>

<div class="table-responsive">
<table class="table table-striped table-bordered">

<tr class="success">
<th>ID</th>
<th>TITLE</th>
<th>DATE OF CREATION</th>
<th>MANAGE</th>
</tr>

<c:forEach items="${departments}" var="department">
<tr>
<td>${department.id}</td>
<td>${department.title}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${department.dateOfCreation}"/></td>
<td>

<sec:authorize access="hasRole('USER')">
<a class="btn btn-primary"  href="<c:url value='/departments/find/${department.id}' />"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
<a class="btn btn-danger" href="<c:url value='/departments/delete/${department.id}' />"><span class="glyphicon glyphicon-remove"></span> Delete</a>
</sec:authorize>

<a class="btn btn-default" href="<c:url value='/studyPrograms/?id=${department.id}' />">Study Programs</a>
</td>

</tr>
</c:forEach>

</table>
</div>

</c:if>

</div>
</div>