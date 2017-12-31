<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h3 class="text-center">Save/Update Professor</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/professors/save?${_csrf.parameterName}=${_csrf.token}"
	method="post" enctype="multipart/form-data" class="form-horizontal"
	modelAttribute="professor">
	<tag:input_text_group name="id" title="ID"></tag:input_text_group>
	<tag:input_date_group name="dateOfEmployment" title="DATE OF EPLOYMENT"></tag:input_date_group>
	<tag:input_text_group name="fullName" title="FIRST NAME"></tag:input_text_group>
	<tag:input_text_group name="fatherName" title="FATHER NAME"></tag:input_text_group>
	<tag:input_select_group_list items="${genders}" name="gender" title="GENDER"></tag:input_select_group_list>
	<tag:input_date_group name="dateOfBirth" title="DATE OF BIRTH"></tag:input_date_group>
	<tag:input_text_group name="email" title="EMAIL"></tag:input_text_group>
	<tag:input_text_group name="telephone" title="TELEPHONE"></tag:input_text_group>
	<tag:input_text_group name="address" title="ADDRESS (city-street-state)"></tag:input_text_group>
	<tag:input_text_group name="title" title="TITLE"></tag:input_text_group>
	<tag:input_file_group></tag:input_file_group>
	<tag:button_grup></tag:button_grup>
</sf:form>


