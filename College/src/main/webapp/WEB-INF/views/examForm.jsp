<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="text-right">
	<a class="btn btn-default"
		href="<c:url value='/exams?studyProgramId=${param.studyProgramId}'/>">Exams</a>
</div>

<h3 class="text-center">Exam&nbsp;Form</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/exams/save?studyProgramId=${param.studyProgramId}"
	method="post" modelAttribute="exam" class="form-horizontal">

	<tag:input_date_group name="date" title="DATE"></tag:input_date_group>

	<tag:input_select_group_with_list_objects items="${subjects}"
		name="subject" itemLabel="title" itemValue="id" title="SUBJECT"></tag:input_select_group_with_list_objects>

	<tag:input_select_group_with_list_objects items="${professors}"
		name="professor" itemLabel="fullName" itemValue="id" title="PROFESSOR"></tag:input_select_group_with_list_objects>

	<tag:input_select_group_with_list_objects items="${students}"
		name="student" itemLabel="fullName" itemValue="id" title="STUDENT"></tag:input_select_group_with_list_objects>

	<tag:input_text_group name="score" title="SCORE"></tag:input_text_group>

	<tag:button_group></tag:button_group>

</sf:form>





