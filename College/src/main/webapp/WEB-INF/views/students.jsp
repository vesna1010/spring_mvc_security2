<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="row">
<div class="col-sm-12">

<c:if test="${empty students}">
<h4 class="text-center text-danger">Empty list!</h4>
</c:if>

<c:if test="${not empty students}">
<h3 class="text-center">Students
<c:if test="${studyProgram!=null}"> at ${studyProgram.title}</c:if></h3><br>

<div class="table-responsive">
<table class="table table-striped table-bordered">
<tr class="active">
<th>ID</th>
<th>DATE OF ENTRY</th>
<th>FIRST NAME</th>
<th>LAST NAME</th>
<th>FATHER NAME</th>
<th>GENDER</th>
<th>DATE OF BIRTH</th>
<th>EMAIL</th>
<th>TELEPHONE</th>
<th>ADDRESS</th>
<th>YEAR OF STUDY</th>
<th>IMAGE</th>

<c:if test="${studyProgram==null}">
<th>STUDY PROGRAM</th>
<th>MANAGE</th>
</c:if>

</tr>
<c:forEach items="${students}" var="student">
<tr>
<td>${student.id}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${student.dateOfEntry}"/></td>
<td>${student.firstName}</td>
<td>${student.lastName}</td>
<td>${student.fatherName}</td>
<td>${student.gender}</td>
<td><fmt:formatDate pattern="dd-MM-yyyy" value="${student.dateOfBirth}"/></td>
<td>${student.email}</td>
<td>${student.telephone}</td>
<td>${student.address}</td>
<td>${student.yearOfStudy}</td>
<td>

<img  src="data:image/jpeg;base64,${student.showImage()}" width="100" height="100"/>

<c:if test="${studyProgram==null}">
<td>${student.studyProgram.title}</td>
<td>

<sec:authorize access="hasRole('USER')">
<a class="btn btn-primary" href="<c:url value='/students/find/${student.id}' />"><span class="glyphicon glyphicon-pencil"></span> Edit</a>
<a class="btn btn-danger" href="<c:url value='/students/delete/${student.id}' />"><span class="glyphicon glyphicon-remove"></span> Delete</a>
</sec:authorize>

<a class="btn btn-default" href="<c:url value='/students/exams/${student.id}' />"> Exams</a>
</td>
</c:if>
</tr>
</c:forEach>
</table>
</div>
</c:if>

</div>
</div>