<%@ page import="javax.portlet.PortletURL,
				 java.util.Map,
				 java.util.Iterator,
				 java.util.List"%>

<%@ taglib uri="webwork" prefix="ww" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<portlet:defineObjects/>

<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Calendar information</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/calendar.css" />
	<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/applications/jscalendar/calendar-system.css" title="system" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/applications/jscalendar/calendar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/applications/jscalendar/lang/calendar-en.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/applications/jscalendar/calendar-setup.js"></script>
	
</head>

<body>

<div id="inputForm">
	
	<div id="contentListHeader">
		Update location <ww:property value="location.name"/>
	</div>

	<div id="contentList">
		<portlet:actionURL var="updateLocationActionUrl">
			<portlet:param name="action" value="UpdateLocation"/>
		</portlet:actionURL>
		
		<form name="inputForm" method="POST" action="<c:out value="${updateLocationActionUrl}"/>">
			<input type="hidden" name="locationId" value="<ww:property value="location.id"/>">
			<p>
				name: <br>
				<input type="textfield" name="name" value="<ww:property value="location.name"/>" class="normalInput">
			</p>
			<p>	
				description: <br>
				<input type="textfield" name="description" value="<ww:property value="location.description"/>" class="normalInput">
			</p>
			<p>
				<input type="submit">
			</p>
		</form>
	</div>

</div>

</body>
</html>