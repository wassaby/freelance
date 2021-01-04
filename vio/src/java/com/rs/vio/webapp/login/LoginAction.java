package com.rs.vio.webapp.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.beans.login.BLoginException;
import com.rs.commons.beans.login.BLoginInfo;
import com.rs.commons.beans.login.IBLogin;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.ForwardAction;
import com.teremok.struts.helper.StrutsHelperConstants;

public class LoginAction extends ForwardAction {

	private static final Logger log = Logger.getLogger(LoginAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession(true);
		
		IBLogin login = null;
		BLoginInfo loginInfo = new BLoginInfo();
		try {
			LoginForm loginForm = (LoginForm) form;
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			login = manager.getLogin();

			loginInfo = login.login(loginForm.getLogin(),
					loginForm.getPassword());

			session.setAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR,	loginInfo);
			return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (BLoginException e) {
			request.setAttribute(VioConstants.LOGIN_ERROR_MESSAGE_ATTR, e.getMessageKey());
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}

}
