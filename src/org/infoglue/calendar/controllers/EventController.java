/* ===============================================================================
*
* Part of the InfoGlue Content Management Platform (www.infoglue.org)
*
* ===============================================================================
*
*  Copyright (C)
* 
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU General Public License version 2, as published by the
* Free Software Foundation. See the file LICENSE.html for more information.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY, including the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with
* this program; if not, write to the Free Software Foundation, Inc. / 59 Temple
* Place, Suite 330 / Boston, MA 02111-1307 / USA.
*
* ===============================================================================
*/

package org.infoglue.calendar.controllers;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infoglue.calendar.entities.Calendar;
import org.infoglue.calendar.entities.Category;
import org.infoglue.calendar.entities.Event;
import org.infoglue.calendar.entities.Location;
import org.infoglue.calendar.entities.Participant;
import org.infoglue.common.security.InfoGluePrincipal;
import org.infoglue.common.security.UserControllerProxy;
import org.infoglue.common.util.PropertyHelper;
import org.infoglue.common.util.VelocityTemplateProcessor;
import org.infoglue.common.util.io.FileHelper;
import org.infoglue.common.util.mail.MailServiceFactory;


import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.hibernate.*;
import net.sf.hibernate.cfg.*;
import net.sf.hibernate.expression.Expression;
import net.sf.hibernate.type.Type;

public class EventController extends BasicController
{    
    //Logger for this class
    private static Log log = LogFactory.getLog(EventController.class);
        
    
    /**
     * Factory method to get EventController
     * 
     * @return EventController
     */
    
    public static EventController getController()
    {
        return new EventController();
    }
        
    
    /**
     * This method is used to create a new Event object in the database.
     */
    
    public Event createEvent(Long calendarId, 
            				String name, 
            				String description, 
            				Boolean isInternal, 
            	            Boolean isOrganizedByGU, 
            	            String organizerName, 
            	            String lecturer, 
            	            String customLocation,
            	            String shortDescription,
            	            String longDescription,
            	            String eventUrl,
            	            String contactName,
            	            String contactEmail,
            	            String contactPhone,
            	            Float price,
            	            java.util.Calendar lastRegistrationCalendar,
            	            Integer maxumumParticipants,
            	            java.util.Calendar startDateTime, 
            	            java.util.Calendar endDateTime, 
            	            String[] locationId, 
            	            String[] categoryId, 
            	            String[] participantUserName,
            	            boolean isPublished) throws HibernateException, Exception 
    {
        Event event = null;
        
        Session session = getSession();
        
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
			
			System.out.println("calendarId:" + calendarId);
	        System.out.println("name:" + name);
	        System.out.println("description:" + description);
	        System.out.println("startCalendar:" + startDateTime);
	        System.out.println("endCalendar:" + endDateTime);
	        System.out.println("locationId:" + locationId);
	        System.out.println("categoryId:" + categoryId);
	        System.out.println("participantUserName:" + participantUserName);
	        
			Calendar calendar = CalendarController.getController().getCalendar(calendarId, session);
			System.out.println("calendar:" + calendar);
			
			Set locations = new HashSet();
			if(locationId != null)
			{
				for(int i=0; i<locationId.length; i++)
				{
				    Location location = LocationController.getController().getLocation(new Long(locationId[i]), session);
				    locations.add(location);
				}
			}
			
			Set categories = new HashSet();
			if(categoryId != null)
			{
				for(int i=0; i<categoryId.length; i++)
				{
				    Category category = CategoryController.getController().getCategory(new Long(categoryId[i]), session);
				    categories.add(category);
				}
			}
			
			Set participants = new HashSet();
			if(participantUserName != null)
			{
				for(int i=0; i<participantUserName.length; i++)
				{
				    Participant participant = new Participant();
				    participant.setUserName(participantUserName[i]);
				    participant.setEvent(event);
				    session.save(participant);
				    participants.add(participant);
				}
			}
			
			event = createEvent(calendar, 
			        			name, 
			        			description, 
			        			isInternal, 
			                    isOrganizedByGU, 
			                    organizerName, 
			                    lecturer, 
			                    customLocation,
			                    shortDescription,
			                    longDescription,
			                    eventUrl,
			                    contactName,
			                    contactEmail,
			                    contactPhone,
			                    price,
			                    lastRegistrationCalendar,
			                    maxumumParticipants,
			        			startDateTime, 
			        			endDateTime, 
			        			locations, 
			        			categories, 
			        			participants,
			        			isPublished,
			        			session);

			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
		
        return event;
    }

    
    /**
     * This method is used to create a new Event object in the database inside a transaction.
     */
    
    public Event createEvent(Calendar calendar, 
            				String name, 
            				String description, 
            				Boolean isInternal, 
            	            Boolean isOrganizedByGU, 
            	            String organizerName, 
            	            String lecturer, 
            	            String customLocation,
            	            String shortDescription,
            	            String longDescription,
            	            String eventUrl,
            	            String contactName,
            	            String contactEmail,
            	            String contactPhone,
            	            Float price,
            	            java.util.Calendar lastRegistrationCalendar,
            	            Integer maxumumParticipants,
            	            java.util.Calendar startDateTime, 
            				java.util.Calendar endDateTime, 
            				Set locations, 
            				Set categories, 
            				Set participants,
            				boolean isPublished,
            				Session session) throws HibernateException, Exception 
    {
        System.out.println("Creating new event...");
        
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);
        event.setIsInternal(isInternal);
        event.setIsOrganizedByGU(isOrganizedByGU);
        event.setOrganizerName(organizerName);
        event.setLecturer(lecturer);
        event.setCustomLocation(customLocation);
        event.setShortDescription(shortDescription);
        event.setLongDescription(longDescription);
        event.setEventUrl(eventUrl);
        event.setContactName(contactName);
        event.setContactEmail(contactEmail);
        event.setContactPhone(contactPhone);
        event.setPrice(price);
        event.setMaxumumParticipants(maxumumParticipants);
        event.setLastRegistrationDateTime(lastRegistrationCalendar);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime); 
        event.setIsPublished(new Boolean(isPublished));
        
        event.setCalendar(calendar);
        event.setLocations(locations);
        event.setCategories(categories);
        event.setParticipants(participants);
        calendar.getEvents().add(event);
        
        session.save(event);
        
        System.out.println("Finished creating event...");
        
        return event;
    }
    
    
    /**
     * Updates an event.
     * 
     * @throws Exception
     */
    
    public void updateEvent(
            Long id, 
            String name, 
            String description, 
            Boolean isInternal, 
            Boolean isOrganizedByGU, 
            String organizerName, 
            String lecturer, 
            String customLocation,
            String shortDescription,
            String longDescription,
            String eventUrl,
            String contactName,
            String contactEmail,
            String contactPhone,
            Float price,
            java.util.Calendar lastRegistrationCalendar,
            Integer maxumumParticipants,
            java.util.Calendar startDateTime, 
            java.util.Calendar endDateTime, 
            String[] locationId, 
            String[] categoryId, 
            String[] participantUserName) throws Exception 
    {
	    Session session = getSession();
	    
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
		
			Event event = getEvent(id, session);
			
			Set locations = new HashSet();
			for(int i=0; i<locationId.length; i++)
			{
			    Location location = LocationController.getController().getLocation(new Long(locationId[i]), session);
			    locations.add(location);
			}

			Set categories = new HashSet();
			for(int i=0; i<categoryId.length; i++)
			{
			    Category category = CategoryController.getController().getCategory(new Long(categoryId[i]), session);
			    categories.add(category);
			}

			Set participants = new HashSet();
			for(int i=0; i<participantUserName.length; i++)
			{
			    Participant participant = new Participant();
			    participant.setUserName(participantUserName[i]);
			    participant.setEvent(event);
			    session.save(participant);
			    participants.add(participant);
			}

			updateEvent(
			        event, 
			        name, 
			        description, 
			        isInternal, 
			        isOrganizedByGU, 
			        organizerName, 
			        lecturer, 
			        customLocation,
	                shortDescription,
	                longDescription,
	                eventUrl,
	                contactName,
	                contactEmail,
	                contactPhone,
	                price,
	                lastRegistrationCalendar,
	                maxumumParticipants,
			        startDateTime, 
			        endDateTime, 
			        locations, 
			        categories, 
			        participants, 
			        session);
			
			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
    }
    
    /**
     * Updates an event inside an transaction.
     * 
     * @throws Exception
     */
    
    public void updateEvent(
            Event event, 
            String name, 
            String description, 
            Boolean isInternal, 
            Boolean isOrganizedByGU, 
            String organizerName, 
            String lecturer, 
            String customLocation,
            String shortDescription,
            String longDescription,
            String eventUrl,
            String contactName,
            String contactEmail,
            String contactPhone,
            Float price,
            java.util.Calendar lastRegistrationCalendar,
            Integer maxumumParticipants,
            java.util.Calendar startDateTime, 
            java.util.Calendar endDateTime, 
            Set locations, 
            Set categories, 
            Set participants, 
            Session session) throws Exception 
    {
        event.setName(name);
        event.setDescription(description);
        event.setIsInternal(isInternal);
        event.setIsOrganizedByGU(isOrganizedByGU);
        event.setOrganizerName(organizerName);
        event.setLecturer(lecturer);
        event.setCustomLocation(customLocation);
        event.setShortDescription(shortDescription);
        event.setLongDescription(longDescription);
        event.setEventUrl(eventUrl);
        event.setContactName(contactName);
        event.setContactEmail(contactEmail);
        event.setContactPhone(contactPhone);
        event.setPrice(price);
        event.setMaxumumParticipants(maxumumParticipants);
        event.setLastRegistrationDateTime(lastRegistrationCalendar);
        event.setStartDateTime(startDateTime);
        event.setEndDateTime(endDateTime);
        event.setLocations(locations);
        event.setCategories(categories);
        event.setParticipants(participants);
        
		session.update(event);
	}
    
 
    /**
     * Publishes an event.
     * 
     * @throws Exception
     */
    
    public void publishEvent(Long id) throws Exception 
    {
	    Session session = getSession();
	    
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
		
			Event event = getEvent(id, session);
			event.setIsPublished(new Boolean(true));
			
			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
    }
    
    
    /**
     * This method returns a Event based on it's primary key
     * @return Event
     * @throws Exception
     */
    
    public Event getEvent(Long id) throws Exception
    {
        Event event = null;
        
        Session session = getSession();
        
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
			event = getEvent(id, session);
			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
		
		return event;
    }
    
    /**
     * This method returns a Event based on it's primary key inside a transaction
     * @return Event
     * @throws Exception
     */
    
    public Event getEvent(Long id, Session session) throws Exception
    {
        Event event = (Event)session.load(Event.class, id);
		
		return event;
    }
    
    
    /**
     * This method returns a list of Events based on a number of parameters
     * @return List
     * @throws Exception
     */
    
    public List getEventList() throws Exception
    {
        List list = null;
        
        Session session = getSession();
        
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();

			list = getEventList(session);
			
			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
		
		return list;
    }
    
    /**
     * Gets a list of all events available for a particular day.
     * @return List of Event
     * @throws Exception
     */
    
    public List getEventList(Session session) throws Exception 
    {
        List result = null;
        
        Query q = session.createQuery("from Event event order by event.id");
   
        result = q.list();
        
        return result;
    }

    
    /**
     * This method returns a list of Events based on a number of parameters
     * @return List
     * @throws Exception
     */
    
    public List getEventList(Long id, boolean isPublished, java.util.Calendar startDate, java.util.Calendar endDate) throws Exception
    {
        List list = null;
        
        Session session = getSession();
        
		Transaction tx = null;
		try 
		{
			tx = session.beginTransaction();
			Calendar calendar = CalendarController.getController().getCalendar(id);
			list = getEventList(calendar, isPublished, startDate, endDate, session);
			tx.commit();
		}
		catch (Exception e) 
		{
		    if (tx!=null) 
		        tx.rollback();
		    throw e;
		}
		finally 
		{
		    session.close();
		}
		
		return list;
    }
    
    
    /**
     * This method returns a list of Events based on a number of parameters within a transaction
     * @return List
     * @throws Exception
     */
    
    public List getEventList(Calendar calendar, boolean isPublished, java.util.Calendar startDate, java.util.Calendar endDate, Session session) throws Exception
    {
        //System.out.println("**********************");
        System.out.println("**********************");
        System.out.println("isPublished:" + isPublished);
        System.out.println("**********************");
        Query q = session.createQuery("from Event as event inner join fetch event.calendar as calendar where event.calendar = ? AND event.isPublished = ? AND event.startDateTime >= ? AND event.endDateTime <= ? order by event.startDateTime");
        q.setEntity(0, calendar);
        q.setBoolean(1, isPublished);
        q.setCalendar(2, startDate);
        q.setCalendar(3, endDate);
        
        List list = q.list();
        
        Iterator iterator = list.iterator();
        while(iterator.hasNext())
        {
            Object o = iterator.next();
            //System.out.println("o:" + o.getClass().getName());
            Event event = (Event)o;
            System.out.println("event:" + event.getName());
        }
        
        //System.out.println("**********************");

        //System.out.println("list:" + list.size());
        
		return list;
    }


    /**
     * Gets a list of events fetched by name.
     * @return List of Event
     * @throws Exception
     */
    
    public List getEvent(String name) throws Exception 
    {
        List events = null;
        
        Session session = getSession();
        
        Transaction tx = null;
        
        try 
        {
            tx = session.beginTransaction();
            
            events = session.find("from Event as event where event.name = ?", name, Hibernate.STRING);
                
            tx.commit();
        }
        catch (Exception e) 
        {
            if (tx!=null) 
                tx.rollback();
            throw e;
        }
        finally 
        {
            session.close();
        }
        
        return events;
    }
    
    
    /**
     * Deletes a event object in the database. Also cascades all events associated to it.
     * @throws Exception
     */
    
    public void deleteEvent(Long id) throws Exception 
    {
        Session session = getSession();
        
        Transaction tx = null;
        
        try 
        {
            tx = session.beginTransaction();
            
            Event event = this.getEvent(id);
            session.delete(event);
            
            tx.commit();
        }
        catch (Exception e) 
        {
            if (tx!=null) 
                tx.rollback();
            throw e;
        }
        finally 
        {
            session.close();
        }
    }
    
    
    /**
     * This method emails the owner of an event the new information and an address to visit.
     * @throws Exception
     */
    
    public void notifyPublisher(Event event, String publishEventUrl) throws Exception
    {
	    String email = "";
	    
	    try
	    {
	        System.out.println("CalendarOwner:" + event.getCalendar().getOwner());
	        InfoGluePrincipal inforgluePrincipal = UserControllerProxy.getController().getUser(event.getCalendar().getOwner());
	        
	        String template;
	        
	        String contentType = PropertyHelper.getProperty("mail.contentType");
	        if(contentType == null || contentType.length() == 0)
	            contentType = "text/html";
	        
	        if(contentType.equalsIgnoreCase("text/plain"))
	            template = FileHelper.getFileAsString(new File(PropertyHelper.getProperty("contextRootPath") + "templates/newEventNotification_plain.vm"));
		    else
	            template = FileHelper.getFileAsString(new File(PropertyHelper.getProperty("contextRootPath") + "templates/newEventNotification_html.vm"));
		    
	        System.out.println("inforgluePrincipal:" + inforgluePrincipal.getEmail());
	        System.out.println("template:" + template);
	        
		    Map parameters = new HashMap();
		    parameters.put("event", event);
		    parameters.put("publishEventUrl", publishEventUrl.replaceAll("\\{eventId\\}", event.getId().toString()));
		    
			StringWriter tempString = new StringWriter();
			PrintWriter pw = new PrintWriter(tempString);
			new VelocityTemplateProcessor().renderTemplate(parameters, pw, template);
			email = tempString.toString();
	    
			String systemEmailSender = PropertyHelper.getProperty("systemEmailSender");
			if(systemEmailSender == null || systemEmailSender.equalsIgnoreCase(""))
				systemEmailSender = "infoglueCalendar@" + PropertyHelper.getProperty("mail.smtp.host");

			System.out.println("email:" + email);
			MailServiceFactory.getService().send(systemEmailSender, inforgluePrincipal.getEmail(), "InfoGlue Calendar - new event waiting", email, contentType, "UTF-8");
		}
		catch(Exception e)
		{
		    e.printStackTrace();
			log.error("The notification was not sent. Reason:" + e.getMessage(), e);
		}
		
    }
    
    public List getAssetKeys()
    {
        List assetKeys = new ArrayList();
        
        int i = 0;
        String assetKey = PropertyHelper.getProperty("assetKey." + i);
        while(assetKey != null && assetKey.length() > 0)
        {
            assetKeys.add(assetKey);
            
            i++;
            assetKey = PropertyHelper.getProperty("assetKey." + i);
        }
        
        return assetKeys;
    }

}