/*
 * Created on 10.01.2005
 *
 * Realsoft Ltd. All Rights reserved.
 * $Id: VioServlet.java,v 1.5 2016/04/15 10:37:42 dauren_home Exp $
 */
package com.rs.vio.webapp;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.struts.action.ActionServlet;
import org.quartz.Scheduler;

import com.teremok.commons.beans.ComponentFactoryImpl;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.utils.formatter.FormatterFactory;
import com.teremok.utils.formatter.FormatterImpl;

/**
 * @author dimad
 */
public class VioServlet extends ActionServlet {

	private static final long serialVersionUID = 1L;

	public static final String LOG4J_CONFIG = "log4j-config";

	public static final String OPERATOR_NAME = "operator-name";

	public static final String OPERATOR_PASSWORD = "operator-password";

	public static final String REPORT_MONTH_COUNT = "report-month-count";

	public static final String SCHEDULER_BEAN = "scheduler";

	private static Logger log = null;

	private IComponentFactory manager = null;
	
	@Override
	public void init() throws ServletException {
		try {
			String loggerConfigFile = getServletConfig().getInitParameter(
					LOG4J_CONFIG);
			if (loggerConfigFile != null) {
				DOMConfigurator.configure(getServletContext().getRealPath(
						"/" + loggerConfigFile));
			}
			log = Logger.getLogger(VioServlet.class);
			java.net.URI componentManagerFile = getClass().getClassLoader().getResource("spring-server.xml").toURI(); 
			manager = ComponentFactoryImpl.getInstance(componentManagerFile.toString(), IComponentFactory.BILLING_MODE);

			log.debug("initializing manager done");

			Scheduler scheduler = (Scheduler) manager.getBean(SCHEDULER_BEAN);
			
			log.debug("Packets reader started");
			
			log.info("Scheduler started");
			
			FormatterFactory formatterFactory = manager.getFormatter();
			formatterFactory.initialize();
			formatterFactory.register(Timestamp.class, new FormatterImpl("%td.%<tm.%<tY %<tH:%<tM:%<tS"));
			formatterFactory.register(Calendar.class, new FormatterImpl("%td.%<tm.%<tY %<tH:%<tM:%<tS"));
			formatterFactory.register(Date.class, new FormatterImpl("%td.%<tm.%<tY %<tH:%<tM:%<tS"));
			formatterFactory.register(Double.class, new FormatterImpl("%.2f"));
			formatterFactory.register(Double.TYPE, new FormatterImpl("%,.2f"));


			getServletContext().setAttribute(VioConstants.MANAGER_ATTR,
					manager);
			log.debug("component manager initialized");

		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("Could not start servlet instance", e);
			throw new ServletException(e.getMessage());
		}
		super.init();
	}

	@Override
	public void destroy() {
		try {
		} catch (Exception e) {
			log.error("Could not offline all trackers", e);
		}
		manager.dispose();
		super.destroy();
	}

}