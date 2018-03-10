<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<h3 class="text-center">Search Exams</h3>
<br>

<sf:form action="${pageContext.request.contextPath}/exams/search"
	method="post" modelAttribute="exam" class="form-horizontal">
	<sf:hidden path="id"></sf:hidden>
	<tag:input_date_group name="date" title="DATE"></tag:input_date_group>
	<tag:input_select_group_set items="${subjects}" name="subject"
		itemLabel="title" itemValue="id" title="SUBJECT"></tag:input_select_group_set>
	<tag:input_select_group_set items="${professors}" name="professor"
		itemLabel="fullName" itemValue="id" title="PROFESSOR"></tag:input_select_group_set>
	<tag:button_group></tag:button_group>
</sf:form>


