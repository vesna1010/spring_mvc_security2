<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Save/Update Department</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/departments/save"
	method="post" modelAttribute="department" class="form-horizontal">
	<tag:input_text_group name="id" title="ID"></tag:input_text_group>
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	<tag:input_date_group name="dateOfCreation" title="DATE OF CREATION"></tag:input_date_group>
	<tag:button_grup></tag:button_grup>
</sf:form>

