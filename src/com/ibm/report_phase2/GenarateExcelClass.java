package com.ibm.report_phase2;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ibm.bean_phase2.DataPointsPhase2Bean;
import com.ibm.dao.DBConnection;
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;

public class GenarateExcelClass {
	public static boolean generateExcel(String reportName, String header, List<DataPointsPhase2Bean> dataList,
			List<String> merchantList, XSSFSheet sheet, XSSFWorkbook workbook) {

		boolean flag = true;

		/*List<Date> dateList = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int myMonth = cal.get(Calendar.MONTH);

		while (myMonth == cal.get(Calendar.MONTH)) {
			dateList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}*/
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		Date startDate = cal.getTime();


		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		Date endDate = cal.getTime();

		List<Date> dateList = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);

		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(endDate);

		while (calendar.before(endCalendar)) {
			Date result = calendar.getTime();
			dateList.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);

		
		String folderPath =  PropertyClass.getFilePath("");

		FileOutputStream out = null;
		System.out.println("Writing into "+folderPath + reportName + "_" + sysdate + ".xlsx  ");
		XSSFCell cell;

		// ************************** Row0 **********************************
		if(merchantList.size()>0)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, merchantList.size())); // Merchant List

		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);

		XSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.WHITE1.getIndex());
		font.setBold(true);
		font.setFontName("Calibri");

		XSSFCellStyle styleBlue = workbook.createCellStyle();
		styleBlue.setFillForegroundColor(new XSSFColor(new java.awt.Color(155, 194, 230)));
		styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleBlue.setBorderTop(BorderStyle.HAIR);
		styleBlue.setBorderBottom(BorderStyle.HAIR);
		styleBlue.setBorderLeft(BorderStyle.HAIR);
		styleBlue.setDataFormat((short) 14);
		styleBlue.setAlignment(HorizontalAlignment.CENTER);
		styleBlue.setVerticalAlignment(VerticalAlignment.CENTER);
		styleBlue.setWrapText(true);
		styleBlue.setFont(font);

		cell = row1.createCell(0);
		cell.setCellValue("Date");
		cell.setCellStyle(styleBlue);

		Font font0 = workbook.createFont();
		font0.setColor(IndexedColors.BLACK1.getIndex());
		font0.setBold(true);
		font0.setFontName("Calibri");

		XSSFCellStyle style0 = workbook.createCellStyle();
		XSSFColor color0 = new XSSFColor(new java.awt.Color(255, 217, 102));
		style0.setFillForegroundColor(color0);
		style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style0.setBorderLeft(BorderStyle.HAIR);
		style0.setBorderRight(BorderStyle.MEDIUM);
		style0.setWrapText(true);
		style0.setAlignment(HorizontalAlignment.CENTER);
		style0.setFont(font0);

		XSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFillForegroundColor(color0);
		style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style1.setBorderLeft(BorderStyle.HAIR);
		style1.setBorderRight(BorderStyle.HAIR);
		style1.setBorderTop(BorderStyle.HAIR);
		style1.setBorderBottom(BorderStyle.HAIR);
		style1.setWrapText(true);
		style1.setAlignment(HorizontalAlignment.CENTER);

		int m = 1, n = merchantList.size(), i, j, p, d = 0;

		XSSFRow row, daterow;
		List<XSSFRow> rowList = new ArrayList<XSSFRow>();

		// date Row code
		for (i = 0; i < dateList.size(); i++) {
			p=1;
			daterow = sheet.createRow(d + 3);
			cell = daterow.createCell(0);
			
			cell.setCellValue(dateList.get(d++));
			cell.setCellStyle(styleBlue);
			for(j=0;j<merchantList.size();j++) {
				cell = daterow.createCell(p++);
				cell.setCellValue(0);
			}
			rowList.add(daterow);
			
			
		}
		// ***************** Row1 ************************
		if(n>m)
		sheet.addMergedRegion(new CellRangeAddress(1, 1, m, n));

		row1.setHeight((short) 500);
		cell = row1.createCell(1);
		cell.setCellStyle(style0);
		cell.setCellValue(header);

		// ************** Row2 ****************************

		row2.setHeight((short) 850);
		cell = row2.createCell(0);
		cell.setCellStyle(styleBlue);
		p = m;
		for (String col : merchantList) {
			cell = row2.createCell(p++);
			cell.setCellValue(col);
			cell.setCellStyle(style1);
			sheet.setColumnWidth(cell.getColumnIndex(), 2500);

		}

		//**************** Data for sheet ******************

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		List<String> dateString = new ArrayList<String>();
		String strDate = "";// formatter.format(date);
		for (d = 0; d < dateList.size(); d++) {
			strDate = formatter1.format(dateList.get(d));
			dateString.add(strDate);
		}

		int r = 0, c = 1;
		for (DataPointsPhase2Bean bean : dataList) {
			c = 1;
			strDate = formatter1.format(bean.getUploadDate());
			if (dateString.contains(strDate) && merchantList.contains(bean.getMerchantId())) {
				r = dateString.indexOf(strDate);
				c = c + merchantList.indexOf(bean.getMerchantId());
				row = rowList.get(r);
				cell = row.createCell(c);
				cell.setCellValue(bean.getCount());
				
			} else {
				System.out.println("Else --> Not Matched");
			}

		}

		// End data for sheet

		try {
			out = new FileOutputStream(new File(folderPath + reportName + "_" + sysdate + ".xlsx"));
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	public static boolean generateTemplate(String sql2, String reportName, String header, String sheetName,
			String param) {

		boolean flag = false;
		String[] fileName = Constants.fileName;
		
		List<String> merchantList = null;
		String sql1 = Constants.merchant_ids_phase2;
		//System.out.println(sql1);
		Connection con = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		List<Date> dateList = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int myMonth = cal.get(Calendar.MONTH);

		while (myMonth == cal.get(Calendar.MONTH)) {
			dateList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = null;
			con = DBConnection.getConnection();
			// for loop for wap & ppd
			for (int i = 0; i < fileName.length; i++) {
				sheet = workbook.createSheet(fileName[i] + " " + sheetName);
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, fileName[i]);
				rs1 = ps1.executeQuery();
				merchantList = new ArrayList<String>();
				while (rs1.next()) {
					merchantList.add(rs1.getString("MERCHANT_ID"));
				}
				//System.out.println(merchantList);
				
				ps2 = con.prepareStatement(sql2);
				ps2.setString(1, fileName[i]);
				rs2 = ps2.executeQuery();

				List<DataPointsPhase2Bean> list = new ArrayList<DataPointsPhase2Bean>();
				while (rs2.next()) {
					DataPointsPhase2Bean bean = new DataPointsPhase2Bean();
					bean.setMerchantId(rs2.getString("MERCHANT_ID"));
					bean.setUploadDate(rs2.getDate("UPLOAD_DATE"));
					bean.setCount(rs2.getLong(param));

					list.add(bean);
				}
				System.out.println();
				System.out.println("###### "+fileName[i]+" Sheet ######");
				if(merchantList.size()>0)
				flag = GenarateExcelClass.generateExcel(reportName, header, list, merchantList, sheet, workbook);
				
				System.out.println("File Generated : " + flag);
			}
			// End For loop

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();

				if (ps1 != null)
					ps1.close();

				if (rs1 != null)
					rs1.close();

				if (ps2 != null)
					ps2.close();

				if (rs2 != null)
					rs2.close();

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		return flag;
	}

}
