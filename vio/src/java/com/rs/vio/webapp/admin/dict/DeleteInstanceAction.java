package com.rs.vio.webapp.admin.dict;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.simple.JSONObject;

import com.daurenzg.commons.beans.vio.dict.IBDict;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;

public class DeleteInstanceAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		DeleteInstanceForm fForm = (DeleteInstanceForm) form;
		
		response.setContentType("text/html");
    	response.setHeader("charset", "UTF-8");
    	OutputStream stream = response.getOutputStream();
    	JSONObject jsonResponse = new JSONObject();
    	JSONObject error = new JSONObject();
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBDict report = manager.getVioDict();
        	report.deleteInstance(fForm.getInstanceId());
        	error.put("error", 0);
        	error.put("msg", "OK");
        	jsonResponse.put("response", "Success");
        	stream.write(jsonResponse.toJSONString().getBytes());
        	stream.flush();
		} catch (CommonsBeansException e){
			error.put("code", e.getErrorCode());
        	error.put("message", e.getMessageKey());
        	jsonResponse.put("error", error);
        	stream.write(jsonResponse.toString().getBytes());
        	stream.flush();
		}
		return null;
	}

}
