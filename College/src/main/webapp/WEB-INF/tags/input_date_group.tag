<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ attribute name="name" required="true" type="java.lang.String"%>
<%@ attribute name="title" required="true" type="java.lang.String"%>

<div class="form-group">
	<sf:label path="${name}" class="control-label col-sm-3 col-sm-offset-2">${title}</sf:label>
	<div class="col-sm-4">
		<div class="input-group">
			<sf:input path="${name}" class="form-control datepicker"
				readonly="true" />
			<span class="input-group-addon showDate"> <span
				class="glyphicon glyphicon-calendar"></span>
			</span>
		</div>
		<sf:errors class="text-danger" path="${name}" />
	</div>
</div>
