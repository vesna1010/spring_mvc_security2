<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="text-right">
	<a class="btn btn-default"
		href="<c:url value='/lectures?studyProgramId=${param.studyProgramId}'/>">Lectures</a>
</div>

<h3 class="text-center">Lecture&nbsp;Form</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/lectures/save?studyProgramId=${param.studyProgramId}"
	method="post" modelAttribute="lecture" class="form-horizontal">

	<tag:input_select_group_with_list_objects items="${professors}"
		name="professor" itemLabel="fullName" itemValue="id" title="PROFESSOR"></tag:input_select_group_with_list_objects>

	<tag:input_select_group_with_list_objects items="${subjects}"
		name="subject" itemLabel="title" itemValue="id" title="SUBJECT"></tag:input_select_group_with_list_objects>

	<tag:input_text_group name="hours" title="HOURS"></tag:input_text_group>

	<tag:button_group></tag:button_group>

</sf:form>



