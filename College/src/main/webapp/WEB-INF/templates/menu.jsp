<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="navbar navbar-inverse">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-target="#navbar"
			data-toggle="collapse">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
	</div>

	<div class="collapse navbar-collapse" id="navbar">
		<ul class="nav navbar-nav">
			<li id="home"><a href='<c:url value="/"/>'>Home</a></li>

			<sec:authorize access="hasRole('ADMIN')">
				<li id="users"><a href='<c:url value="/users"/>'>Users</a></li>
				<li id="userForm"><a href='<c:url value="/users/form"/>'>User&nbsp;Form</a></li>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<li id="changePassword"><a href='<c:url value="/users/edit"/>'>Change&nbsp;Password</a></li>
			</sec:authorize>

			<li id="departments"><a href='<c:url value="/departments"/>'>Departments</a></li>

			<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
				<li id="departmentForm"><a
					href='<c:url value="/departments/form"/>'>Department&nbsp;Form</a></li>
			</sec:authorize>

			<li id="studyPrograms"><a
				href='<c:url value="/studyPrograms/"/>'>Study&nbsp;Programs</a></li>

			<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
				<li id="studyProgramForm"><a
					href='<c:url value="/studyPrograms/form"/>'>Study&nbsp;Program&nbsp;Form</a></li>
			</sec:authorize>

			<li id="professors"><a href='<c:url value="/professors"/>'>Professors</a></li>

			<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
				<li id="professorForm"><a
					href='<c:url value="/professors/form"/>'>Professor&nbsp;Form</a></li>
			</sec:authorize>

			<li id="subjects"><a href='<c:url value="/subjects"/>'>Subjects</a></li>

			<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
				<li id="subjectForm"><a href='<c:url value="/subjects/form"/>'>Subject&nbsp;Form</a></li>
			</sec:authorize>

			<li id="students"><a href='<c:url value="/students/"/>'>Students</a></li>

			<sec:authorize access="hasAnyRole('USER', 'ADMIN')">
				<li id="studentForm"><a href='<c:url value="/students/form"/>'>Student&nbsp;Form</a></li>
			</sec:authorize>

			<li id="searchExamsForm"><a
				href='<c:url value="/exams/search"/>'>Search&nbsp;Exams</a></li>
		</ul>
	</div>

</div>




