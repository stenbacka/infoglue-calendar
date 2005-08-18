//$Id: HibernateInterceptor.java,v 1.1 2005/08/18 14:01:08 mattias Exp $
package org.infoglue.common.interceptor;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.infoglue.calendar.actions.CalendarAbstractAction;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/**
 * @author Gavin King
 */
public class HibernateInterceptor implements Interceptor {
	
	private static final Log LOG = LogFactory.getLog(HibernateInterceptor.class);
	
	public void destroy() {}

	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception {
		Action action = invocation.getAction();
		System.out.println("");
		System.out.println("");
		System.out.println("INTERCEPTOR:" + action);
		System.out.println("");
		System.out.println("");
		
		if ( !(action instanceof CalendarAbstractAction) ) return invocation.invoke();
		
		CalendarAbstractAction calendarAction = (CalendarAbstractAction)action;
		try {
			return invocation.invoke();
		}
		
		// Note that all the cleanup is done
		// after the view is rendered, so we
		// have an open session in the view
		
		catch (Exception e) {	
		    calendarAction.setRollBackOnly(true);
			if (e instanceof HibernateException) {
				LOG.error("HibernateException in execute()", e);
				return Action.ERROR;
			}
			else {
				LOG.error("Exception in execute()", e);
				throw e;
			}
		}
		
		finally {
			try {
			    calendarAction.disposeSession();
			}
			catch (HibernateException e) {
				LOG.error("HibernateException in dispose()", e);
				return Action.ERROR;
			}
		}
	}

}