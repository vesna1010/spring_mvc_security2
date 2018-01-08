<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Save/Update Study Program</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/studyPrograms/save"
	method="post" modelAttribute="studyProgram" class="form-horizontal">
	<tag:input_text_group name="id" title="ID"></tag:input_text_group>
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	<tag:input_date_group name="dateOfCreation" title="DATE OF CREATION"></tag:input_date_group>
	<tag:input_text_group name="durationOfStudy" title="DURATION OF STUDY"></tag:input_text_group>
	<tag:input_select_group_set items="${departments}" name="department"
		itemLabel="title" itemValue="id" title="DEPARTMENT"></tag:input_select_group_set>
	<tag:button_grup></tag:button_grup>
</sf:form>

