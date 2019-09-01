<%@ page import="college.enums.Role"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Update&nbsp;Password</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/users/update"
	method="post" modelAttribute="user" class="form-horizontal">

	<sf:hidden path="id" />

	<sf:hidden path="enabled" />

	<sf:hidden path="name" />

	<sf:hidden path="email" />

	<sf:hidden path="roles" />

	<tag:input_password_group name="password" title="PASSWORD"></tag:input_password_group>

	<tag:input_password_group name="confirmPassword"
		title="CONFIRM PASSWORD"></tag:input_password_group>

	<tag:button_group></tag:button_group>

</sf:form>
