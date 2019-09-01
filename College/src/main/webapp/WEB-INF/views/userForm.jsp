<%@ page import="college.enums.Role"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">User&nbsp;Form</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/users/save"
	method="post" modelAttribute="user" class="form-horizontal">

	<sf:hidden path="id" />

	<sf:hidden path="enabled" />

	<tag:input_text_group name="name" title="NAME"></tag:input_text_group>

	<tag:input_text_group name="email" title="EMAIL"></tag:input_text_group>

	<tag:input_password_group name="password" title="PASSWORD"></tag:input_password_group>

	<tag:input_password_group name="confirmPassword"
		title="CONFIRM PASSWORD"></tag:input_password_group>

	<tag:input_select_group_array items="${Role.values()}" name="roles"
		title="ROLES"></tag:input_select_group_array>

	<tag:button_group></tag:button_group>

</sf:form>



