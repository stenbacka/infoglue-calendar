<%@ taglib uri="webwork" prefix="ww" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="calendar" prefix="calendar" %>

<portlet:defineObjects/>


<!-- Calendar start -->
<div class="calendar"> 	
	<span class="categoryLabel">
		<ww:iterator value="event.owningCalendar.eventType.categoryAttributes" status="rowstatus">
			<ww:if test="top.name == 'Evenemangstyp' || top.name == 'Eventtyp'">
				<ww:set name="selectedCategories" value="this.getEventCategories(top)"/>
				<ww:iterator value="#selectedCategories">
					<ww:property value="top.name"/>
				</ww:iterator>
			</ww:if>
   		</ww:iterator>
	</span> 	
	
	<H1><ww:property value="event.name"/></H1>

<!-- Detta skall ev vara med beroende p� hur articelkomponenten ser ut
	<div class="record">
	<span class="categoryLabelSmall">�ppen f�rel�sning</span> -->
	
	<ww:if test="event.decoratedShortDescription != null && event.decoratedShortDescription != ''">
		<h4><ww:property value="event.decoratedShortDescription"/></h4>
	</ww:if>
	
	<ww:if test="event.decoratedLongDescription != null && event.decoratedLongDescription != ''">
	<p>
	<ww:set name="puffImage" value="this.getResourceUrl(event, 'DetaljBild')"/>
	<ww:if test="#puffImage != null">
	<img src="<ww:property value="#puffImage"/>" class="img_left_letter"/>
	</ww:if>
	<ww:property value="event.decoratedLongDescription"/>
	</p>
	</ww:if>
	
	<div class="calFact">
		<p><span class="calFactLabel">F&ouml;rel&auml;sare: </span><ww:property value="event.lecturer"/></p>
			<ww:set name="startDate" value="this.formatDate(event.startDateTime.time, 'yyyy-MM-dd')"/>
			<ww:set name="endDate" value="this.formatDate(event.endDateTime.time, 'yyyy-MM-dd')"/>
			<ww:if test="#startDate != #endDate">
				<p><span class="calFactLabel">Datum & tid: </span><ww:property value="#startDate"/> kl <ww:property value="this.formatDate(event.startDateTime.time, 'HH:mm')"/> till <ww:property value="#endDate"/> kl <ww:property value="this.formatDate(event.endDateTime.time, 'HH:mm')"/></p>                             		
			</ww:if>
			<ww:else>
				<p><span class="calFactLabel">Datum: </span><ww:property value="#startDate"/></p>                             		
				<p><span class="calFactLabel">Tid: </span><ww:property value="this.formatDate(event.startDateTime.time, 'HH:mm')"/> - <ww:property value="this.formatDate(event.endDateTime.time, 'HH:mm')"/></p>
			</ww:else>
			<p><span class="calFactLabel">Kategori: </span>
			<ww:iterator value="event.owningCalendar.eventType.categoryAttributes" status="rowstatus">
				<ww:if test="top.name == '�mnesomr�de' || top.name == '�mnesomr�den'">
					<ww:set name="selectedCategories" value="this.getEventCategories(top)"/>
					<ww:iterator value="#selectedCategories">
						<ww:property value="top.name"/>
					</ww:iterator>
				</ww:if>
	   		</ww:iterator>
   		</p>
   		
   		<ww:if test="event.organizerName != null && event.organizerName != ''">
   			<p><span class="calFactLabel">Arrang&ouml;r: </span><ww:property value="event.organizerName"/></p>
		</ww:if>
		
		<p><span class="calFactLabel">Plats: </span><br/>
			<ww:iterator value="event.locations">
	      		<ww:set name="location" value="top"/>
 				<ww:property value='#location.name'/><br/>		
      		</ww:iterator>
			<ww:property value="event.customLocation"/>
		</p>
		<%--
		<p><span class="calFactLabel">Adress: </span><ww:property value="event.customLocation"/></p>
		--%>
		<p><span class="calFactLabel">Evenemangsl&auml;nk: </span><a href="<ww:property value="event.eventUrl"/>">L�s mer om <ww:property value="event.name"/></a></p>

		<p><span class="calFactLabel">Ytterliggare information: </span><A href="mailto:<ww:property value="event.contactEmail"/>"><ww:property value="event.contactEmail"/></A></p>
		
		<p><span class="calFactLabel">Ytterliggare information: </span><br/>
		<ww:iterator value="event.resources">
  		  <ww:if test="top.assetKey == 'BifogadFil'">
			<ww:set name="resourceId" value="top.id" scope="page"/>
			<calendar:resourceUrl id="url" resourceId="${resourceId}"/>
			<ww:if test="fileName.indexOf('.pdf') > -1">
				<ww:set name="resourceClass" value="'pdficon'"/>
			</ww:if>
			<ww:if test="fileName.indexOf('.doc') > -1">
				<ww:set name="resourceClass" value="'wordicon'"/>
			</ww:if>
			<ww:if test="fileName.indexOf('.xls') > -1">
				<ww:set name="resourceClass" value="'excelicon'"/>
			</ww:if>
			<ww:if test="fileName.indexOf('.ppt') > -1">
				<ww:set name="resourceClass" value="'powerpointicon'"/>
			</ww:if>
			<a href="<c:out value="${url}"/>" target="_blank" class="<ww:property value="#resourceClass"/>"><ww:property value="fileName"/></a><br/>
  		  </ww:if>
  		</ww:iterator>
  		</p>
		
		<ww:if test="event.lastRegistrationDateTime != null">
   			<p><span class="calFactLabel">Sista anm&auml;lningsdag: </span>snarast, dock senast <ww:property value="this.formatDate(event.lastRegistrationDateTime.time, 'd MMM')"/> kl. <ww:property value="this.formatDate(event.lastRegistrationDateTime.time, 'HH')"/>.</p>
		</ww:if>
		
		<p><span class="calFactLabel">Avgift:</span> <ww:property value="event.price.intValue()"/>:- </p>
		<%--
		<p><span class="calFactLabel">Anm�lan:</span>
		<ww:if test="event.lastRegistrationDateTime.time.time > now.time.time">
			<ww:if test="event.maximumParticipants > event.entries.size()">
				<ww:set name="eventId" value="eventId" scope="page"/>
				<portlet:renderURL var="createEntryRenderURL">
					<calendar:evalParam name="action" value="CreateEntry!inputPublicGU"/>
					<calendar:evalParam name="eventId" value="${eventId}"/>
				</portlet:renderURL>
				<a href="<c:out value="${createEntryRenderURL}"/>"><ww:property value="this.getLabel('labels.public.event.signUp')"/></a></span>
			</ww:if>
			<ww:else>
					<ww:property value="this.getLabel('labels.public.maximumEntriesReached.title')"/>
			</ww:else>
		</ww:if>
		<ww:else>
			<ww:property value="this.getLabel('labels.public.event.registrationExpired')"/>
		</ww:else>
		</p>
		--%>
	</div>
<!-- </div> -->
</div>
<!-- Calendar End -->

