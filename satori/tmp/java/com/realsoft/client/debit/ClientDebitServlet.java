/*
 * Created on 10.01.2005
 *
 * Realsoft Ltd. All Rights reserved.
 * $Id: ClientDebitServlet.java,v 1.1 2014/07/01 11:58:26 dauren_work Exp $
 */
package com.realsoft.client.debit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.struts.action.ActionServlet;

import com.realsoft.commons.beans.ComponentFactoryImpl;
import com.realsoft.commons.beans.IComponentFactory;
import com.realsoft.utils.formatter.FormatterImpl;
import com.realsoft.utils.formatter.IFormatter;

/**
 * @author dimad
 */
public class ClientDebitServlet extends ActionServlet {

	private static final long serialVersionUID = 1L;

	public static final String LOG4J_CONFIG = "log4j-config";
    
    public static final String TOWN_CODE = "town-code";

    public static final String OPERATOR_NAME = "operator-name";

    public static final String OPERATOR_PASSWORD = "operator-password";
    
    public static final String REPORT_MONTH_COUNT = "report-month-count";

    private static Logger log = null;
    
    private IComponentFactory manager = null;

    public void init() throws ServletException {
        try {
            String loggerConfigFile = getServletConfig().getInitParameter(LOG4J_CONFIG);
            if (loggerConfigFile != null) {
                DOMConfigurator.configure(getServletContext().getRealPath(
                        "/" + loggerConfigFile));
            }
            log = Logger.getLogger(ClientDebitServlet.class);
            manager = ComponentFactoryImpl.getInstance(
                    getClass().getClassLoader()
                            .getResource("spring-server.xml").toURI()
                            .toString(), IComponentFactory.BILLING_MODE);
            
            log.debug("initializing manager done");
            IFormatter formatter = manager.getFormatter();
            formatter.register(Date.class, new FormatterImpl(
                    "%td.%<tm.%<tY %<tH:%<tM"));
            formatter.register(GregorianCalendar.class, new FormatterImpl(
                    "%td.%<tm.%<tY %<tH:%<tM"));
            formatter.register(Timestamp.class, new FormatterImpl(
                    "%td.%<tm.%<tY %<tH:%<tM:%<tS.%<tL"));

            getServletContext().setAttribute(ClientDebitConstants.MANAGER_ATTR,
                    manager);
            log.debug("component manager initialized");
            
        } catch (Exception e) {
            log.fatal("Could not start servlet instance", e);
            throw new ServletException(e.getMessage());
        }
        super.init();
    }
    
    public void destroy() {
	manager.dispose();
	super.destroy();
    }

}