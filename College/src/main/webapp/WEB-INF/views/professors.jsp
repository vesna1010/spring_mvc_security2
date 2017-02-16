<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${empty professors}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty professors}">
<h3 class="text-center">Professors
<c:if test="${studyProgram!=null}"> at ${studyProgram.title}</c:if></h3><br>

<div class="table-responsive">
<table class="table table-striped table-bordered">
<tr class="success">
<th>ID</th>
<th>DATE OF EMPLOYMENT</th>
<th>FIRST NAME</th>
<th>LAST NAME</th>
<th>FATHER NAME</th>
<th>GENDER</th>
<th>DATE OF BIRTH</th>
<th>EMAIL</th>
<th>TELEPHONE</th>
<th>ADDRESS</th>
<th>TITLE</th>
<th>DATE OF TERMINATION</th>
<th>IMAGE</th>
<th>SUBJECTS</th>

<c:if test="${studyProgram==null}">
<sec:authorize access="hasRole('USER')">
<th>MANAGE</th>
</sec:authorize>
</c:if>

</tr>
<c:forEach items="${professors}" var="professor">
<tr>
<td>${professor.id}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${professor.dateOfEmployment}"/></td>
<td>${professor.firstName}</td>
<td>${professor.lastName}</td>
<td>${professor.fatherName}</td>
<td>${professor.gender}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${professor.dateOfBirth}"/></td>
<td>${professor.email}</td>
<td>${professor.telephone}</td>
<td>${professor.address}</td>
<td>${professor.title}</td>
<td>${professor.dateOfTermination}</td>
<td><img  src="data:image/jpeg;base64,${professor.showImage()}" width="100" height="100"/></td>
<td>
<c:forEach items="${professor.subjects}" var="subject">
${subject.title}<br>
</c:forEach>
</td>

<c:if test="${studyProgram==null}">
<sec:authorize access="hasRole('USER')">
<td>
<a class="btn btn-primary" href="<c:url value='/professors/find/${professor.id}' />"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
<a class="btn btn-danger" href="<c:url value='/professors/delete/${professor.id}' />"><span class="glyphicon glyphicon-remove"></span> Delete</a>
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