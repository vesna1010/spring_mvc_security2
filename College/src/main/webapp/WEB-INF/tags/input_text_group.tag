<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="errors" required="false" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>

<div class="form-group">
	<sf:label path="${name}" class="control-label col-sm-3 col-sm-offset-2">${title}</sf:label>
	<div class="col-sm-4">
		<sf:input path="${name}" class="form-control" />
		<sf:errors class="text-danger" path="${name}" />
		<sf:errors class="text-danger" path="${errors}" />
	</div>
</div>
