<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Subject&nbsp;Form</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/subjects/save"
	method="post" class="form-horizontal" modelAttribute="subject">
	
	<sf:hidden path="id" />
	
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	
	<tag:input_select_group_with_list_objects items="${studyPrograms}"
		name="studyProgram" itemLabel="title" itemValue="id"
		title="STUDY PROGRAM"></tag:input_select_group_with_list_objects>
		
	<tag:button_group></tag:button_group>
	
</sf:form>

