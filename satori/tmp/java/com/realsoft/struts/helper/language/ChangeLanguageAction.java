/*
 * Created on 04.05.2005
 *
 * Realsoft Ltd.
 * Dudorga Dmitry.
 * $Id: ChangeLanguageAction.java,v 1.1 2014/07/01 11:58:28 dauren_work Exp $
 */
package com.realsoft.struts.helper.language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.realsoft.struts.helper.ForwardAction;
import com.realsoft.struts.helper.StrutsHelperConstants;

/**
 * @author dimad
 */
public class ChangeLanguageAction extends ForwardAction {

	private static Logger log = Logger.getLogger(ChangeLanguageAction.class);

	public ChangeLanguageAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ChangeLanguageForm languageForm = (ChangeLanguageForm) form;
		log.debug("changing locale to " + languageForm.getLanguage());
		HttpSession session = request.getSession(true);
		session.setAttribute(Globals.LOCALE_KEY, new Locale(languageForm
				.getLanguage()));
		request.setAttribute(StrutsHelperConstants.SUCCESS_MSG_ATTR,
				"change-language.success.message");
		String prevAction = (String) session
				.getAttribute(StrutsHelperConstants.CURRENT_ACTION_ATTR);
		log.debug("currAction = " + prevAction);
		prevAction = (prevAction == null) ? "/"
				+ StrutsHelperConstants.INDEX_FORWARD + ".do" : prevAction
				+ ".do";
		log.debug("currAction = " + prevAction);
		return new ActionForward(prevAction);
		// return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
	}

}
