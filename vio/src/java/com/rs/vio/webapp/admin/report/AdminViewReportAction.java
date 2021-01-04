package com.rs.vio.webapp.admin.report;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

import com.rs.commons.vio.report.BInstanceItem;
import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.BReportStatusItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.rs.vio.webapp.webform.ViewReportAction;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class AdminViewReportAction extends AbstractAdminAction {

	private static final Logger log = Logger.getLogger(ViewReportAction.class);

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		AdminViewReportForm viewReportForm = (AdminViewReportForm) form;
		String charset = "UTF-8";
		String USER_AGENT = request.getHeader("user-agent");
		StringBuffer url = new StringBuffer().append("http://maps.googleapis.com/maps/api/geocode/json?sensor=false&language=ru&latlng=");
		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();

			List<BInstanceItem> instanceList = report.getInstances();
			List<BReportStatusItem> statusList = report.getReportStatuses();
			
			BReportItem reportItem = report.getReport(viewReportForm
					.getReportId());
			
			String urlString = url.append(reportItem.getLat()).append(",").append(reportItem.getLon()).toString();
	    	URL obj = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("Accept-Charset", charset);
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
			String inputLine;
			StringBuffer responseS = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				responseS.append(inputLine);
			}
			in.close();
	    	
			JSONObject jsonObj = new JSONObject(responseS.toString());
			JSONArray res = jsonObj.getJSONArray("results");
			String address = "Не получено данных от сервера Google.";
			if (!res.isNull(0)){
				JSONObject addressObj = res.getJSONObject(0);
				address = addressObj.getString("formatted_address");
			}
			request.setAttribute(VioConstants.ADDRESS, address);
			request.setAttribute(VioConstants.ADMIN_VIEW_REPORT_ATTR, reportItem);
			session.setAttribute(VioConstants.INSTANCES, instanceList);
			session.setAttribute(VioConstants.REPORT_STATUSES, statusList);
			
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e) {
			log.error("Error in getting report attributes ", e);
			request.setAttribute(VioConstants.ALL_ERRORS, e.getMessageKey());
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
