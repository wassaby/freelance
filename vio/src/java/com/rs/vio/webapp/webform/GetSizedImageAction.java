package com.rs.vio.webapp.webform;


import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.IBFile;

public class GetSizedImageAction extends Action {

	Logger log = Logger.getLogger(GetSizedImageAction.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		GetSizedImageForm getSignedStickerForm = (GetSizedImageForm) form;
		try {
        	IComponentFactory manager = (IComponentFactory)getServlet().getServletContext().getAttribute(VioConstants.MANAGER_ATTR);
        	IBFile dynamicImage = manager.getFile();
        	
			response.setContentType("image/jpeg");
			OutputStream stream = response.getOutputStream();
			
			BufferedImage bi = dynamicImage.getSizedImage(getSignedStickerForm.getFileId(), getSignedStickerForm.getWidth(), getSignedStickerForm.getHeight());
			
			ImageIO.write(bi, "JPG", stream);
			return null;
        } catch (Exception e) {
            return null;
        }
	}

}
