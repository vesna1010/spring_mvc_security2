<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>
<%@ attribute name="items" required="true" type="java.lang.Enum[]"%>

<div class="form-group">
	<sf:label path="${name}" class="control-label col-sm-3 col-sm-offset-2">${title}</sf:label>
	<div class="col-sm-4">
		<sf:select path="${name}" class="form-control" items="${items}">
			<sf:option value=""></sf:option>
		</sf:select>
		<sf:errors class="text-danger" path="${name}" />
	</div>
</div>
