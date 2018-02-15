<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<h3 class="text-center">Save/Update User</h3>
<br>

<h4 class="text-center text-success">${message}</h4>

<sf:form action="${pageContext.request.contextPath}/users/save"
	method="post" modelAttribute="user" class="form-horizontal">
	<sec:authorize access="hasRole('ADMIN')">
		<tag:input_text_group name="username" title="USERNAME"></tag:input_text_group>
		<tag:input_text_group name="email" title="EMAIL"></tag:input_text_group>
	</sec:authorize>
	<tag:input_password_group name="password" title="PASSWORD"></tag:input_password_group>
	<tag:input_password_group name="confirmPassword"
		title="CONFIRM PASSWORD"></tag:input_password_group>
	<sec:authorize access="hasRole('ADMIN')">
		<tag:input_select_group_list items="${roles}" name="roles"
			title="ROLES"></tag:input_select_group_list>
	</sec:authorize>
	<tag:button_group></tag:button_group>
</sf:form>
