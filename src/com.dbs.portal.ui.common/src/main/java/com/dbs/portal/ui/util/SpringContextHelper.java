package com.dbs.portal.ui.util;

import javax.portlet.PortletContext;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.portlet.context.PortletApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.WebApplicationContext;

public class SpringContextHelper {

	private ApplicationContext context;

    public SpringContextHelper(Application application) {
    	com.vaadin.service.ApplicationContext appContext = application.getContext();
    	if (appContext instanceof WebApplicationContext) {
    		ServletContext servletContext = ((WebApplicationContext)appContext).getHttpSession().getServletContext();
    		context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	}else if (appContext instanceof PortletApplicationContext2) {
    		PortletContext portletContext = ((PortletApplicationContext2)appContext).getPortletSession().getPortletContext();
    		context = PortletApplicationContextUtils.getRequiredWebApplicationContext(portletContext);    		
    	}
        
    }

    public ApplicationContext getContext() {
    	return context;
    }
    
    public Object getBean(final String beanRef) {
        return context.getBean(beanRef);
    }    
}
