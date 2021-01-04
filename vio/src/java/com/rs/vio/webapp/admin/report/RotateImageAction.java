package com.rs.vio.webapp.admin.report;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;
import com.teremok.commons.beans.file.BFileItem;
import com.teremok.commons.beans.file.IBFile;
import com.teremok.struts.helper.StrutsHelperConstants;

public class RotateImageAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		RotateImageForm fForm = (RotateImageForm) form;

		try {
			IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();
			IBFile file = manager.getFile();
			BFileItem fileItem = file.getFile(fForm.getFileId());
			InputStream is = new ByteArrayInputStream(fileItem.getData());
			BufferedImage bImage = ImageIO.read(is);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			BufferedImage b2Image = rotate(bImage, 90);//
			ImageIO.write(b2Image, "jpg", baos);
			byte[] imageInByte = baos.toByteArray();

			file.updateImage(fForm.getFileId(), imageInByte);

			baos.flush();
			baos.close();
			
			BReportItem reportItem = report.getReport(fForm.getReportId());
        	request.setAttribute(VioConstants.ADMIN_VIEW_REPORT_ATTR, reportItem);
        	return mapping.findForward(StrutsHelperConstants.SUCCESS_FORWARD);
		} catch (CommonsBeansException e) {
			return mapping.findForward(StrutsHelperConstants.ERROR_FORWARD);
		}
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
		int width = image.getWidth(null);
	      int height = image.getHeight(null);
	      BufferedImage temp = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
	      Graphics2D g2 = temp.createGraphics();
	      g2.rotate(Math.toRadians(angle), height / 2, height / 2);
	      g2.drawImage(image, 0, 0, Color.WHITE, null);
	      g2.dispose();
	      return temp;
	}

}
