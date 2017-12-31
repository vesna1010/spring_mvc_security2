<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>
<%@ attribute name="items" required="true" type="java.util.Set"%>
<%@ attribute name="itemValue" required="true" type="java.lang.String"%>
<%@ attribute name="itemLabel" required="true" type="java.lang.String"%>

<div class="form-group">
	<sf:label path="${name}" class="control-label col-sm-3 col-sm-offset-2">${title}</sf:label>
	<div class="col-sm-4">
		<sf:select path="${name}" class="form-control">
			<sf:option value=""></sf:option>
			<sf:options items="${items}" itemLabel="${itemLabel}"
				itemValue="${itemValue}" />
		</sf:select>
		<sf:errors class="text-danger" path="${name}" />
	</div>
</div>
