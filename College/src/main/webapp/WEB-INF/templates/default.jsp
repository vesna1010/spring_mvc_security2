<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript"
	src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="<c:url value='/resource/jQuery/date.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resource/jQuery/activeClass.js' />"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css">
<link rel="stylesheet" href="<c:url value='/resource/css/style.css' />" />

</head>
<body>
	<div class="container">
		<input type='hidden' name="page"
			value="<tiles:getAsString name='active'/>" />

		<div class="row">
			<div class="col-sm-12 header hidden-xs hidden-sm">
				<tiles:insertAttribute name="header" />
			</div>
		</div>

		<div class="row">
			<div class="col-sm-2">

				<tiles:insertAttribute name="menu" />
			</div>
			<div class="col-sm-10 body">
				<br>
				<tiles:insertAttribute name="body" />
			</div>
		</div>

	</div>
</body>
</html>
