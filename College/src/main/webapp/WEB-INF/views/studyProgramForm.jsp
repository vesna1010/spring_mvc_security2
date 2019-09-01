<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Study&nbsp;Program&nbsp;Form</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/studyPrograms/save"
	method="post" modelAttribute="studyProgram" class="form-horizontal">
	
	<sf:hidden path="id" />
	
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	
	<tag:input_date_group name="dateOfCreation" title="DATE OF CREATION"></tag:input_date_group>
	
	<tag:input_text_group name="durationOfStudy" title="DURATION OF STUDY"></tag:input_text_group>
	
	<tag:input_select_group_with_list_objects items="${departments}"
		name="department" itemLabel="title" itemValue="id" title="DEPARTMENT">
		
	</tag:input_select_group_with_list_objects>
	
	<tag:button_group></tag:button_group>
	
</sf:form>

