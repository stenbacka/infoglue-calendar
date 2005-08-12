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

package org.infoglue.calendar.actions;

import java.util.Iterator;
import java.util.List;

import org.infoglue.calendar.controllers.CalendarController;
import org.infoglue.calendar.controllers.CategoryController;
import org.infoglue.calendar.entities.Calendar;
import org.infoglue.common.security.UserControllerProxy;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.validator.ValidationException;

/**
 * This action represents a Calendar Administration screen.
 * 
 * @author Mattias Bogeblad
 */

public class CreateCalendarAction extends CalendarAbstractAction
{
    private String name;
    private String description;
    private String owner;
    
    private List infogluePrincipals;

    
    /**
     * This is the entry point for the main listing.
     */
    
    public String execute() throws Exception 
    {
        try
        {
            validateInput(this);
            CalendarController.getController().createCalendar(name, description, owner);
        }
        catch(ValidationException e)
        {
            return Action.ERROR;            
        }
        
        return Action.SUCCESS;
    } 

    /**
     * This is the entry point creating a new calendar.
     */
    
    public String input() throws Exception 
    {
        this.infogluePrincipals = UserControllerProxy.getController().getAllUsers();

        return Action.INPUT;
    } 
    
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getOwner()
    {
        return owner;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    public List getInfogluePrincipals()
    {
        return infogluePrincipals;
    }
}
