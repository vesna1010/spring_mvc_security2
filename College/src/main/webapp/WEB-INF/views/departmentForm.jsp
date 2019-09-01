<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Department&nbsp;Form</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/departments/save"
	method="post" modelAttribute="department" class="form-horizontal">
	
	<sf:hidden path="id" />
	
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	
	<tag:input_date_group name="dateOfCreation" title="DATE OF CREATION"></tag:input_date_group>
	
	<tag:button_group></tag:button_group>
	
</sf:form>
