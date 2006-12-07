<%@ taglib uri="webwork" prefix="ww" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="calendar" prefix="calendar" %>

<portlet:defineObjects/>

<ww:iterator value="events" status="rowstatus">

<ww:if test="#rowstatus.count <= numberOfItems">

	<ww:set name="event" value="top"/>
	<ww:set name="eventVersion" value="this.getEventVersion('#event')"/>
	<ww:set name="eventVersion" value="this.getEventVersion('#event')" scope="page"/>

	<div class="record">
		<ww:if test="#attr.detailUrl.indexOf('?') > -1">
			<c:set var="delim" value="&"/>
		</ww:if>
		<ww:else>
			<c:set var="delim" value="?"/>
		</ww:else>

		<h3><a href="<ww:property value="#attr.detailUrl"/><c:out value="${delim}"/>eventId=<ww:property value="top.id"/>"><ww:property value="#eventVersion.name"/></a></h3>
		<span class="newsdate"><ww:property value="this.formatDate(top.startDateTime.getTime(), 'd MMM')"/> 
		<ww:if test="this.formatDate(top.startDateTime.time, 'HH:mm') != '12:34'">
		<ww:property value="this.getLabel('labels.public.event.klockLabel')"/> <ww:property value="this.formatDate(top.startDateTime.getTime(), 'HH.mm')"/>
		</ww:if>
		<ww:iterator value="top.owningCalendar.eventType.categoryAttributes">
			<ww:if test="top.name == 'Evenemangstyp' || top.name == 'Eventtyp'">
				<ww:set name="selectedCategories" value="this.getEventCategories('#event', top)"/>
				[<ww:iterator value="#selectedCategories" status="rowstatus"><ww:property value="top.name"/><ww:if test="!#rowstatus.last">,</ww:if></ww:iterator>]
			</ww:if>
   		</ww:iterator>
		</span>
		<ww:set name="puffImage" value="this.getResourceUrl(top, 'PuffBild')"/>
		<ww:if test="#puffImage != null">
			<img src="<ww:property value="#puffImage"/>" width="30" height="30"/>
		</ww:if>
	</div>

</ww:if>

</ww:iterator>
