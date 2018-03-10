<%@ page import="college.enums.Gender" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>

<h3 class="text-center">Save/Update A Student</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/students/save?${_csrf.parameterName}=${_csrf.token}"
	method="post" class="form-horizontal" modelAttribute="student"
	enctype="multipart/form-data">
	<tag:input_text_group name="id" title="ID"></tag:input_text_group>
	<tag:input_date_group name="dateOfEntry" title="DATE OF ENTRY"></tag:input_date_group>
	<tag:input_text_group name="fullName" title="FULL NAME"></tag:input_text_group>
	<tag:input_text_group name="fatherName" title="FATHER NAME"></tag:input_text_group>
	<tag:input_text_group name="yearOfStudy" title="YEAR OF STUDY"></tag:input_text_group>
	<tag:input_select_group_set items="${studyPrograms}"
		name="studyProgram" title="STUDY PROGRAM" itemLabel="title"
		itemValue="id"></tag:input_select_group_set>
	<tag:input_select_group_list items="${Gender.values()}" name="gender"
		title="GENDER"></tag:input_select_group_list>
	<tag:input_date_group name="dateOfBirth" title="DATE OF BIRTH"></tag:input_date_group>
	<tag:input_text_group name="email" title="EMAIL"></tag:input_text_group>
	<tag:input_text_group name="telephone" title="TELEPHONE"></tag:input_text_group>
	<tag:input_text_group name="address" errors="address.*"
		title="ADDRESS (city-street-state)"></tag:input_text_group>
	<tag:input_file_group name="image" file="file" title="IMAGE"></tag:input_file_group>
	<tag:button_group></tag:button_group>
</sf:form>

