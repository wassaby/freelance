package com.rs.vio.webapp.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetAddressAction extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GetAddressForm fForm = (GetAddressForm) form;
		OutputStream stream = response.getOutputStream();
		
		String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=43.2397335302487,76.9486504700752&sensor=false&language=ru";
		String USER_AGENT = "Mozilla/5.0";
		
		//String charset = java.nio.charset.StandardCharsets.UTF_8.name();
		String charset = "UTF-8";
		
		URL obj = new URL(url);
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
		
		stream.write(responseS.toString().getBytes());
		//stream.write(request.getUserPrincipal().toString().getBytes());
    	stream.flush();
		//request.setAttribute(VioConstants.TEST, responseS);
		//return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
    	return null;
	}
}
