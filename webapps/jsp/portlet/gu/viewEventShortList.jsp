<%@ taglib uri="webwork" prefix="ww" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="calendar" prefix="calendar" %>

<portlet:defineObjects/>

<ww:iterator value="events" status="rowstatus">

<ww:if test="#rowstatus.count <= numberOfItems">

	<ww:set name="event" value="top"/>

	<div class="newspadding">
		<ww:if test="#attr.detailUrl.indexOf('?') > -1">
			<c:set var="delim" value="&"/>
		</ww:if>
		<ww:else>
			<c:set var="delim" value="?"/>
		</ww:else>

		<a href="<ww:property value="#attr.detailUrl"/><c:out value="${delim}"/>eventId=<ww:property value="top.id"/>" class="Headline"><ww:property value="name"/></a><br />
		<span class="newsdate"><ww:property value="this.formatDate(top.startDateTime.getTime(), 'd MMM')"/> kl <ww:property value="this.formatDate(top.startDateTime.getTime(), 'HH.mm')"/>
		<ww:iterator value="top.owningCalendar.eventType.categoryAttributes" status="rowstatus">
			<ww:if test="top.name == 'Evenemangstyp' || top.name == 'Eventtyp'">
				<ww:set name="selectedCategories" value="this.getEventCategories('#event', top)"/>
				<ww:iterator value="#selectedCategories">
					[<ww:property value="top.name"/>]
				</ww:iterator>
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
