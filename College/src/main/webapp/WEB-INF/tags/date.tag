<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ attribute name="date" required="true" type="java.util.Date"%>

<fmt:formatDate pattern="dd-MM-yyyy" value="${date}" />
