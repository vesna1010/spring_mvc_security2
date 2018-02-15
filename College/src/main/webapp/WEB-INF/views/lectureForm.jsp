<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<h3 class="text-center">Save/Update Lecture</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/lectures/save?studyProgramId=${studyProgram.id}"
	method="post" modelAttribute="lecture" class="form-horizontal">
	<tag:input_text_group name="id" title="ID"></tag:input_text_group>
	<tag:input_select_group_set items="${studyProgram.subjects}"
		name="subject" itemLabel="title" itemValue="id" title="SUBJECT"></tag:input_select_group_set>
	<tag:input_select_group_set items="${professors}" name="professor"
		itemLabel="fullName" itemValue="id" title="PROFESSOR"></tag:input_select_group_set>
	<tag:input_text_group name="hours" title="HOURS"></tag:input_text_group>
	<tag:button_group></tag:button_group>
</sf:form>

