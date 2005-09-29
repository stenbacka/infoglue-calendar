<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<%@ include file="adminHeader.jsp" %>

<div class="head"><ww:property value="this.getLabel('labels.internal.application.chooseCalendar')"/></div>

<%@ include file="functionMenu.jsp" %>

<p>
	<ww:property value="this.getLabel('labels.internal.application.chooseCalendarIntro')"/>
</p>
<div>
	
<div class="columnlabelarea">
	<div class="columnLong"><p><ww:property value="this.getLabel('labels.internal.calendar.name')"/></p></div>
	<div class="columnMedium"><p><ww:property value="this.getLabel('labels.internal.calendar.description')"/></p></div>
	<div class="clear"></div>
</div>

	<ww:iterator value="calendars" status="rowstatus">
		
		<ww:set name="calendarId" value="id" scope="page"/>
		<portlet:renderURL var="createEventUrl">
			<portlet:param name="action" value="CreateEvent!input"/>
			<portlet:param name="calendarId" value="<%= pageContext.getAttribute("calendarId").toString() %>"/>
		</portlet:renderURL>
		
		<ww:if test="#rowstatus.odd == true">
	    	<div class="oddrow">
	    </ww:if>
	    <ww:else>
			<div class="evenrow">
	    </ww:else>
	
	       	<div class="columnLong">
	       		<p class="portletHeadline"><a href="<c:out value="${createEventUrl}"/>" title="Visa Kalender"><ww:property value="name"/></a></p>
	       	</div>
	       	<div class="columnMedium">
	       		<p><ww:property value="description"/></p>
	       	</div>
	       	<div class="columnEnd">
	       	</div>
	       	<div class="clear"></div>
	    </div>
			
	</ww:iterator>
</div>

<%@ include file="adminFooter.jsp" %>
