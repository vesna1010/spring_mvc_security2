<%@ page import="college.enums.Gender"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Professor&nbsp;Form</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/professors/save?${_csrf.parameterName}=${_csrf.token}"
	method="post" enctype="multipart/form-data" class="form-horizontal"
	modelAttribute="professor">

	<sf:hidden path="id" />

	<tag:input_text_group name="fullName" title="FULL NAME"></tag:input_text_group>

	<tag:input_date_group name="dateOfEmployment" title="DATE OF EMPLOYMENT"></tag:input_date_group>

	<tag:input_text_group name="fatherName" title="FATHER NAME"></tag:input_text_group>

	<tag:input_date_group name="dateOfBirth" title="DATE OF BIRTH"></tag:input_date_group>

	<tag:input_text_group name="email" title="EMAIL"></tag:input_text_group>

	<tag:input_text_group name="telephone" title="TELEPHONE"></tag:input_text_group>

	<tag:input_select_group_array items="${Gender.values()}" name="gender"
		title="GENDER"></tag:input_select_group_array>

	<tag:input_text_group name="address.state" title="ADDRESS STATE"></tag:input_text_group>

	<tag:input_text_group name="address.city" title="ADDRESS CITY"></tag:input_text_group>

	<tag:input_text_group name="address.street" title="ADDRESS STREET"></tag:input_text_group>

	<tag:input_text_group name="address.zipCode" title="ADDRESS ZIP CODE"></tag:input_text_group>

	<tag:input_file_group name="image" file="file" title="IMAGE"></tag:input_file_group>

	<tag:input_date_group name="dateOfEmployment" title="DATE OF EPLOYMENT"></tag:input_date_group>

	<tag:input_text_group name="titleOfProfessor" title="TITLE"></tag:input_text_group>

	<tag:button_group></tag:button_group>

</sf:form>


