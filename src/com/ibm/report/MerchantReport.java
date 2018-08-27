package com.ibm.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.ibm.bean.MerchantAnalysisBean;
import com.ibm.dao.DBConnection;
import com.ibm.util.PropertyClass;

public class MerchantReport {

	public static boolean excelFileMerchantAnalysis(FileOutputStream out,
			XSSFSheet sheet, XSSFWorkbook workbook,
			HashMap<String, List<MerchantAnalysisBean>> map,
			List<String> merchantList, List<Date> dateList) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = "";// formatter.format(date);
		boolean flag = true;
		List<String> list = new ArrayList<String>();
		list.add("API1 Requests");
		list.add("API1 Duplicates");
		list.add("API1 Timeouts");
		list.add("API 2 Requests");
		list.add("API 2 - No User Response");
		list.add("Consents");
		list.add("No");
		list.add("Null XY");
		list.add("Activations");
		list.add("% API1 Duplicates");
		list.add("% API1 Timeouts");
		list.add("% API 2 Requests");
		list.add("% Null XY");
		list.add("% Consent to API 1 Requests");
		list.add("% Activations to Consent");

		// XSSFWorkbook workbook = new XSSFWorkbook();
		// XSSFSheet sheet = workbook.createSheet("Merchant Analysis");
		XSSFCell cell;

		// ************************** Row0 **********************************
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, list.size()
				* merchantList.size()));

		XSSFRow row1 = sheet.createRow(1);
		XSSFRow row2 = sheet.createRow(2);

		XSSFFont font = workbook.createFont();
		font.setColor(IndexedColors.WHITE1.getIndex());
		font.setBold(true);
		font.setFontName("Calibri");

		XSSFCellStyle styleBlue = workbook.createCellStyle();
		styleBlue.setFillForegroundColor(new XSSFColor(new java.awt.Color(155,
				194, 230)));
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

		int m = 1, n = list.size(), j = 0, i, p, d = 0;

		XSSFRow row, daterow;
		List<XSSFRow> rowList = new ArrayList<XSSFRow>();

		// date Row code
		for (i = 0; i < dateList.size(); i++) {
			daterow = sheet.createRow(d + 3);
			cell = daterow.createCell(0);
			cell.setCellValue(dateList.get(d++));
			cell.setCellStyle(styleBlue);
			rowList.add(daterow);
		}

		for (i = 0; i < map.size(); i++) {

			// ***************** Row1 ************************
			sheet.addMergedRegion(new CellRangeAddress(1, 1, m, n));
			row1.setHeight((short) 500);
			cell = row1.createCell(m);
			cell.setCellStyle(style0);
			cell.setCellValue(merchantList.get(i));

			// ************** Row2 ****************************

			row2.setHeight((short) 850);
			cell = row2.createCell(0);
			cell.setCellStyle(styleBlue);
			p = m;
			for (String col : list) {
				cell = row2.createCell(p++);
				cell.setCellValue(col);
				cell.setCellStyle(style1);
				sheet.setColumnWidth(cell.getColumnIndex(), 2500);

			}
			// *************** Data Row ***********************************

			List<MerchantAnalysisBean> l = map.get(merchantList.get(i));
			for (MerchantAnalysisBean bean : l) {

				j = m;
				for (d = 0; d < dateList.size(); d++) {
					strDate = formatter.format(dateList.get(d));
					if (strDate.equals((bean.getUPLOAD_DATE().toString()))) {

						row = rowList.get(d);

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI1_REQUESTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI1_DUPLICATES());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI1_TIMEOUTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI2_REQUESTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI2_NOUSER_RESPONSE());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getCONSENTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI2_NO());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getNULL_XY());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getACTIVATIONS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI1_PERC_DUPLICATES());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI1_PERC_TIMEOUTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getAPI2_PERC_REQUESTS());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getNULL_PERC_XY());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getCONSENT_PERC_API1());

						cell = row.createCell(j++);
						cell.setCellValue(bean.getACTIVATIONS_PERC_TO_CONSENT());
					} else {
					}
				}

			}
			m = n + 1;
			n = n + list.size();

		}

		return flag;
	}

	// public static void mainMerchantAnalysis() {
	public static void main(String[] args) {

		 String folderPath = PropertyClass.getFilePath(""); //
		// SIT -
		// 13
		// server
		//String folderPath = "C:/Users/DhirendraSingh/Desktop/CGReportFolder/"; // Local
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);
	//	String[] fileName = { "WAP", "PPD" };
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Merchant Analysis");
		FileOutputStream out = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		Connection con = null;
		try {
			out = new FileOutputStream(new File(folderPath
					+ "Merchant_Analysis_Report_" + sysdate + ".xlsx"));

			System.out
					.println("Writing in Merchant Analysis work sheet ......");
			String sql1 = "select distinct(MERCHANT_ID) from merchant_analysis";
			String sql2 = "select UPLOAD_DATE, MERCHANT_ID, API1_REQUESTS, API1_DUPLICATES, API1_TIMEOUTS, API2_REQUESTS, API2_NOUSER_RESPONSE, CONSENTS, API2_NO, NULL_XY, ACTIVATIONS, API1_PERC_DUPLICATES, API1_PERC_TIMEOUTS,API2_PERC_REQUESTS, NULL_PERC_XY, CONSENT_PERC_API1,ACTIVATIONS_PERC_TO_CONSENT from merchant_analysis where MERCHANT_ID = ?";

			con = DBConnection.getConnection();

			List<MerchantAnalysisBean> list = null;
			HashMap<String, List<MerchantAnalysisBean>> map = new HashMap<String, List<MerchantAnalysisBean>>();
			List<String> merchantList = new ArrayList<String>();

			ps1 = con.prepareStatement(sql1);
			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				merchantList.add(rs1.getString("MERCHANT_ID"));
			}

			for (String li : merchantList) {
				list = new ArrayList<MerchantAnalysisBean>();
				ps2 = con.prepareStatement(sql2);
				ps2.setString(1, li);
				rs2 = ps2.executeQuery();

				while (rs2.next()) {

					MerchantAnalysisBean bean = new MerchantAnalysisBean();
					bean.setUPLOAD_DATE(rs2.getDate("UPLOAD_DATE"));
					bean.setMERCHANT_ID(rs2.getString("MERCHANT_ID"));
					bean.setAPI1_REQUESTS(rs2.getInt("API1_REQUESTS"));
					bean.setAPI1_DUPLICATES(rs2.getInt("API1_DUPLICATES"));
					bean.setAPI1_TIMEOUTS(rs2.getInt("API1_TIMEOUTS"));
					bean.setAPI2_REQUESTS(rs2.getInt("API2_REQUESTS"));
					bean.setAPI2_NOUSER_RESPONSE(rs2
							.getInt("API2_NOUSER_RESPONSE"));
					bean.setCONSENTS(rs2.getInt("CONSENTS"));
					bean.setAPI2_NO(rs2.getInt("API2_NO"));
					bean.setNULL_XY(rs2.getInt("NULL_XY"));
					bean.setACTIVATIONS(rs2.getInt("ACTIVATIONS"));
					bean.setAPI1_PERC_DUPLICATES(rs2
							.getString("API1_PERC_DUPLICATES"));
					bean.setAPI1_PERC_TIMEOUTS(rs2
							.getString("API1_PERC_TIMEOUTS"));
					bean.setAPI2_PERC_REQUESTS(rs2
							.getString("API2_PERC_REQUESTS"));
					bean.setNULL_PERC_XY(rs2.getString("NULL_PERC_XY"));
					bean.setCONSENT_PERC_API1(rs2
							.getString("CONSENT_PERC_API1"));
					bean.setACTIVATIONS_PERC_TO_CONSENT(rs2
							.getString("ACTIVATIONS_PERC_TO_CONSENT"));

					list.add(bean);
				}
				map.put(li, list);
			}

			List<Date> dateList = new ArrayList<Date>();

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			int myMonth = cal.get(Calendar.MONTH);

			while (myMonth == cal.get(Calendar.MONTH)) {
				dateList.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}

			boolean flag = false;
			flag = excelFileMerchantAnalysis(out, sheet, workbook, map,
					merchantList, dateList);
			workbook.write(out);
			System.out.println("File Generated : " + flag);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (con != null)
					con.close();
				if (ps1 != null)
					ps1.close();
				if (ps2 != null)
					ps2.close();
				if (rs1 != null)
					rs1.close();
				if (rs2 != null)
					rs2.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
