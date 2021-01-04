package com.rs.vio.webapp.admin.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.rs.commons.vio.report.BReportItem;
import com.rs.commons.vio.report.IBReport;
import com.rs.vio.webapp.AbstractAdminAction;
import com.rs.vio.webapp.VioConstants;
import com.teremok.commons.beans.CommonsBeansException;
import com.teremok.commons.beans.IComponentFactory;

public class DownloadXLSAction extends AbstractAdminAction {

	@Override
	protected ActionForward executeAdminAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(true);
		DownloadXLSForm xlsForm = (DownloadXLSForm) form;
		HSSFWorkbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    HSSFSheet sheet = wb.createSheet("Reports");
	    
	    List<BReportItem> reportList = new ArrayList<BReportItem>();
	    
	    try{
	    	IComponentFactory manager = (IComponentFactory) getServlet()
					.getServletContext()
					.getAttribute(VioConstants.MANAGER_ATTR);
			IBReport report = manager.getReport();
			if (xlsForm.getReportStatusId() == 0)
				reportList = report.getAdminReportListAllByStatus();
			else 
				reportList = report.getAdminReportListAllByStatus(xlsForm.getReportStatusId());
	    	int i = 1;
	    	
	    	HSSFRow header = sheet.createRow(0);
	        header.createCell(0).setCellValue("����� ������");
	        header.createCell(1).setCellValue("��� ���������");
	        header.createCell(2).setCellValue("����");
	        header.createCell(3).setCellValue("������");
	        header.createCell(4).setCellValue("�����������");
	        header.createCell(5).setCellValue("������� ����");
	        header.createCell(6).setCellValue("����������");
	        header.createCell(7).setCellValue("����������� �� �������");
	        header.createCell(8).setCellValue("������������");
	        header.createCell(9).setCellValue("������");
	        
	    	for (Iterator iter = reportList.iterator(); iter.hasNext();){
	    		BReportItem reportItem = (BReportItem) iter.next();
	    		HSSFRow row = sheet.createRow((short)i);
	    		CellStyle cellStyle = wb.createCellStyle();
	    		switch ((int) reportItem.getStatusId()){
					case 1:
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					case 2:
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					case 3: 
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					case 4: 
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					case 5: 
						cellStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					case 6:
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
					default:
						cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
						cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
						break;
				}
	    		
	    		HSSFCell reportNumCell = row.createCell(0);
	    		reportNumCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	    		reportNumCell.setCellValue(reportItem.getReportId());
	    		//��� ���������
	    		HSSFCell reportCategoryCell = row.createCell(1);
	    		reportCategoryCell.setCellType(Cell.CELL_TYPE_STRING);
	    		reportCategoryCell.setCellValue(reportItem.getReportTypeName());
	    		//����
	    		CellStyle cellDateStyle = wb.createCellStyle();
	    	    cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.mm.yyyy"));
	    		HSSFCell reportDateCell = row.createCell(2);
	    		reportDateCell.setCellStyle(cellDateStyle);
	    		reportDateCell.setCellType(Cell.CELL_TYPE_STRING);
	    		reportDateCell.setCellValue(reportItem.getDate());
	    		//������
	    		HSSFCell reportStatusCell = row.createCell(3);
	    		reportStatusCell.setCellType(Cell.CELL_TYPE_STRING);
	    		reportStatusCell.setCellValue(reportItem.getStatus());
	    		//�����������
	    		HSSFCell reportCommentCell = row.createCell(4);
	    		reportCommentCell.setCellType(Cell.CELL_TYPE_STRING);
	    		reportCommentCell.setCellValue(reportItem.getComment());
	    		//������� ����
	    		HSSFCell reportWithPhotoCell = row.createCell(5);
	    		reportWithPhotoCell.setCellType(Cell.CELL_TYPE_STRING);
	    		if (!reportItem.getListReportFileId().isEmpty()) reportWithPhotoCell.setCellValue("��");
	    		else reportWithPhotoCell.setCellValue("���");
	    		//����������
	    		HSSFCell reportGeoCell = row.createCell(6);
	    		reportGeoCell.setCellType(Cell.CELL_TYPE_STRING);
	    		if (reportItem.getLat() != null && reportItem.getLon() != null) 
	    			reportGeoCell.setCellValue("��");
	    		else reportGeoCell.setCellValue("���");
	    		//����������� �� �������
	    		HSSFCell reportCommentAkimatCell = row.createCell(7);
	    		reportCommentAkimatCell.setCellType(Cell.CELL_TYPE_STRING);
	    		reportCommentAkimatCell.setCellValue(reportItem.getReportHistory().get(0).getComment());
	    		//������������
	    		HSSFCell reportPublishedCell = row.createCell(9);
	    		reportPublishedCell.setCellType(Cell.CELL_TYPE_STRING);
	    		if (reportItem.getPublished() == 1) reportPublishedCell.setCellValue("��");
	    		else reportPublishedCell.setCellValue("���");
	    		//������
	    		HSSFCell reportSolvedCell = row.createCell(10);
	    		reportSolvedCell.setCellType(Cell.CELL_TYPE_STRING);
	    		if (reportItem.getStatusId() == 6) reportSolvedCell.setCellValue("��");
	    		else reportSolvedCell.setCellValue("���");
	    		
	    		
	    		reportStatusCell.setCellStyle(cellStyle);
	    		reportNumCell.setCellStyle(cellStyle);
	    		reportCategoryCell.setCellStyle(cellStyle);
	    		reportDateCell.setCellStyle(cellStyle);
	    		reportStatusCell.setCellStyle(cellStyle);
	    		reportCommentCell.setCellStyle(cellStyle);
	    		reportWithPhotoCell.setCellStyle(cellStyle);
	    		reportGeoCell.setCellStyle(cellStyle);
	    		reportCommentAkimatCell.setCellStyle(cellStyle);
	    		reportPublishedCell.setCellStyle(cellStyle);
	    		reportSolvedCell.setCellStyle(cellStyle);
	    		 
	    		//row.setRowStyle(cellStyle);
	    		i++;
	    	}
	    	
	    	sheet.autoSizeColumn(0);
		    sheet.autoSizeColumn(1);
		    sheet.autoSizeColumn(2);
		    sheet.autoSizeColumn(3);
		    sheet.autoSizeColumn(4);
		    sheet.autoSizeColumn(5);
		    sheet.autoSizeColumn(6);
		    sheet.autoSizeColumn(7);
		    sheet.autoSizeColumn(8);
		    sheet.autoSizeColumn(9);
	    	
	    } catch (CommonsBeansException e) {
			request.setAttribute(VioConstants.ALL_ERRORS, e.getMessageKey());
		}

	    // Create a row and put some cells in it. Rows are 0 based.
	    response.setContentType("application/vnd.ms-excel");
    	response.setHeader("Content-Disposition", new StringBuffer().append("attachment; filename=").append("report.xls").toString());
    	wb.write(response.getOutputStream()); // Write workbook to response.
		
		return null;
	}
}
