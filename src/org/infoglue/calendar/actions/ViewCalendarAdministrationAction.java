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

import javax.portlet.PortletURL;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

/**
 * @author Mattias Bogeblad
 */
public class ViewCalendarAdministrationAction extends CalendarAbstractAction
{
    private String result; 
    
    public String execute() throws Exception 
    {
        System.out.println("In ViewCalendarAdministrationAction.execute");
        
        //response.setContentType("text/html");
        //PortletURL url = response.createActionURL();
        //System.out.println("url:" + url);
        //response.getWriter().println("doView APAPAPAPA <a href");
        
        //return Action.NONE;
        
        if (result == null) 
        {
            result = Action.SUCCESS;
        }
        
        return result;
        
        
    } 

}
