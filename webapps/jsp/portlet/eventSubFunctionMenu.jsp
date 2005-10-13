<portlet:renderURL var="createEventUrl">
	<portlet:param name="action" value="ViewCalendarList!choose"/>
</portlet:renderURL>
<portlet:renderURL var="viewPublishedEventsUrl">
	<portlet:param name="action" value="ViewPublishedEventList"/>
</portlet:renderURL>
<portlet:renderURL var="viewWaitingEventsUrl">
	<portlet:param name="action" value="ViewWaitingEventList"/>
</portlet:renderURL>
<portlet:renderURL var="viewEventSearchFormUrl">
	<portlet:param name="action" value="ViewEventSearch!input"/>
</portlet:renderURL>
	
<div class="subfunctionarea">
	
	<ww:if test="infoGlueRemoteUser != 'eventPublisher'">
		<a href="<c:out value="${createEventUrl}"/>" <c:if test="${activeEventSubNavItem == 'NewEvent'}">class="current"</c:if>><ww:property value="this.getLabel('labels.internal.event.addEvent')"/></a> |
	   	<a href="<c:out value="${viewPublishedEventsUrl}"/>" <c:if test="${activeEventSubNavItem == 'PublishedEvents'}">class="current"</c:if>><ww:property value="this.getLabel('labels.internal.applicationPublishedEvents')"/></a> |
	   	<a href="<c:out value="${viewWaitingEventsUrl}"/>" <c:if test="${activeEventSubNavItem == 'WaitingEvents'}">class="current"</c:if>><ww:property value="this.getLabel('labels.internal.applicationWaitingEvents')"/></a> |
	   	<a href="<c:out value="${viewMyWorkingEventsUrl}"/>" <c:if test="${activeEventSubNavItem == 'MyWorkingEvents'}">class="current"</c:if>><ww:property value="this.getLabel('labels.internal.applicationMyWorkingEvents')"/></a> |
		<a href="<c:out value="${viewEventSearchFormUrl}"/>" <c:if test="${activeEventSubNavItem == 'EventSearch'}">class="current"</c:if>><ww:property value="this.getLabel('labels.internal.applicationSearchEvents')"/></a>
	</ww:if>	
	&nbsp;

</div>