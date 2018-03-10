<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="text-right">
	<a class="btn btn-default"
		href="<c:url value='/lectures?studyProgram=${studyProgram.id}'/>">&nbsp;Lectures</a>
</div>

<h3 class="text-center">Save/Update Lecture</h3>
<br>

<sf:form
	action="${pageContext.request.contextPath}/lectures/save?studyProgram=${studyProgram.id}"
	method="post" modelAttribute="lecture" class="form-horizontal">
	<sf:hidden path="id" />
	<tag:input_select_group_set items="${studyProgram.subjects}"
		name="subject" itemLabel="title" itemValue="id" title="SUBJECT"></tag:input_select_group_set>
	<tag:input_select_group_set items="${professors}" name="professor"
		itemLabel="fullName" itemValue="id" title="PROFESSOR"></tag:input_select_group_set>
	<tag:input_text_group name="hours" title="HOURS"></tag:input_text_group>
	<tag:button_group></tag:button_group>
</sf:form>


