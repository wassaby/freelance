package com.daurenzg.satori.webapp.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.daurenzg.commons.beans.satori.login.BLoginException;
import com.daurenzg.commons.beans.satori.login.BLoginInfo;
import com.daurenzg.commons.beans.satori.login.IBLogin;
import com.daurenzg.satori.webapp.SatoriConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.struts.helper.StrutsHelperConstants;

public class LoginAction extends Action {
	public LoginAction() {
		super();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			LoginForm loginForm = (LoginForm) form;

			HttpSession session = request.getSession(true);

			IComponentFactory manager = (IComponentFactory) session
					.getServletContext().getAttribute(
							SatoriConstants.MANAGER_ATTR);
			IBLogin login = manager.getSatoriLogin();

			BLoginInfo loginInfo = login.login(loginForm.getLogin(),
					loginForm.getPassword());
			session.setAttribute(StrutsHelperConstants.LOGIN_INFO_ATTR,
					loginInfo);

			switch ((int) loginInfo.getAccountTypeId()) {
			case SatoriConstants.USER_TYPE_MANAGER:
				return mapping.findForward("manager");
			case SatoriConstants.USER_TYPE_ADMIN:
				return mapping.findForward("admin");
			default:
				return mapping.findForward("error");
			}

		}

		catch (BLoginException e) {
			request.setAttribute(SatoriConstants.ERROR_MSG_KEY_ATTR, e.getMessageKey());
			return mapping.findForward("error");
		}

	}
}
