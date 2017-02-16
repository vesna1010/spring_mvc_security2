<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar navbar-inverse">
<div class="navbar-header">
<button type="button" class="navbar-toggle"
data-target="#navbar" data-toggle="collapse">
<span class="icon-bar"></span> 
<span class="icon-bar"></span> 
<span class="icon-bar"></span>
</button>
</div>
<div class="collapse navbar-collapse" id="navbar">
<ul class="nav navbar-nav">
<li id="home"><a href='<c:url value="/"/>' >Home</a></li>

<sec:authorize access="hasRole('ADMIN')"> 
<li id="users"><a href='<c:url value="/users/"/>'>All Users</a></li>
<li id="userForm"><a href='<c:url value="/users/save"/>'>Add New User</a></li>
</sec:authorize> 
<sec:authorize access="isAuthenticated()">
<li id="password"><a href='<c:url value="/password/save"/>'>Change Password</a></li>
</sec:authorize>
<li id="departments"><a href='<c:url value="/departments/"/>'>All Departments</a></li>
<sec:authorize access="hasRole('USER')">
<li id="departmentForm"><a href='<c:url value="/departments/save"/>'>Add New Department</a></li>
</sec:authorize>
<li id="studyPrograms"><a href='<c:url value="/studyPrograms/"/>'>All Study Programs</a></li>
<sec:authorize access="hasRole('USER')">
<li id="courseForm"><a href='<c:url value="/studyPrograms/save"/>'>Add New Study Program</a></li>
</sec:authorize>
<li id="professors"><a href='<c:url value="/professors/"/>'>All Professors</a></li>
<sec:authorize access="hasRole('USER')">
<li id="professorForm"><a href='<c:url value="/professors/save"/>'>Add New Professor</a></li>
</sec:authorize>
<li id="subjects"><a href='<c:url value="/subjects/"/>'>All Subjects</a></li>
<sec:authorize access="hasRole('USER')">
<li id="subjectForm"><a href='<c:url value="/subjects/save"/>'>Add New Subject</a></li>
</sec:authorize>
<li id="students"><a href='<c:url value="/students/"/>'>All Students</a></li>
<sec:authorize access="hasRole('USER')">
<li id="studentForm"><a href='<c:url value="/students/save"/>'>Add Student</a></li>
</sec:authorize>
<li id="searchExamsForm"><a href='<c:url value="/exams/search"/>'>Search Exams</a></li>
</ul>
</div>
</div>




