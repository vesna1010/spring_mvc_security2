<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Save/Update Subject</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/subjects/save"
	method="post" class="form-horizontal" modelAttribute="subject">
	<tag:input_text_group name="id" title="ID" errors="id"></tag:input_text_group>
	<tag:input_text_group name="title" title="TITLE" errors="title"></tag:input_text_group>
	<tag:input_select_group_set items="${studyPrograms}" name="studyProgram"
		itemLabel="title" itemValue="id" title="STUDY PROGRAM"></tag:input_select_group_set>
	<tag:button_grup></tag:button_grup>
</sf:form>
