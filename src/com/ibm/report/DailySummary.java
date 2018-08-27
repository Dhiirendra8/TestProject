package com.ibm.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean.DailySummaryBean;
import com.ibm.dao.DBConnection;
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;



public class DailySummary {

	static boolean excelFileMerchantAnalysis(FileOutputStream out,
			XSSFSheet sheet, XSSFWorkbook workbook, List<DailySummaryBean> list) {
		boolean flag = true;
		List<String> headerList = new ArrayList<String>();
		headerList.add("Partner name");
		headerList.add("Product Name");
		headerList.add("Requests Received");
		headerList.add("No User Response");
		headerList.add("Yes");
		headerList.add("No");
		headerList.add("None");
		headerList.add("Null xy");
		headerList.add("Time Out (User Click > 60 secs)");
		headerList.add("% Yes");
		headerList.add("Activations");
		headerList.add("Activation %");

		XSSFCell cell;
		XSSFRow row0 = sheet.createRow(0);

		Font font0 = workbook.createFont();
		font0.setColor(IndexedColors.BLACK1.index);
		font0.setBold(true);
		font0.setFontName("Calibri");
		
		XSSFCellStyle style0 = workbook.createCellStyle();
		XSSFColor color0 = new XSSFColor(new java.awt.Color(255, 217, 102));
		style0.setFillForegroundColor(color0);
		style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style0.setBorderLeft(BorderStyle.HAIR);
		style0.setBorderRight(BorderStyle.HAIR);
		style0.setWrapText(true);
		style0.setAlignment(HorizontalAlignment.CENTER);
		style0.setVerticalAlignment(VerticalAlignment.CENTER);
		style0.setFont(font0);
		
		
		
		Font fontRED = workbook.createFont();
		fontRED.setColor(IndexedColors.RED.index);
		fontRED.setFontName("Calibri");
		fontRED.setBold(true);
		
		XSSFColor colorRED = new XSSFColor(new java.awt.Color(255, 217, 102));
		XSSFCellStyle styleRED = workbook.createCellStyle();
		styleRED.setFillForegroundColor(colorRED);
		styleRED.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleRED.setBorderLeft(BorderStyle.HAIR);
		styleRED.setBorderRight(BorderStyle.HAIR);
		styleRED.setWrapText(true);
		styleRED.setAlignment(HorizontalAlignment.CENTER);
		styleRED.setVerticalAlignment(VerticalAlignment.CENTER);
		styleRED.setFont(fontRED);
		
		
		int i = 0, j = 0;
		for (String col : headerList) {
			cell = row0.createCell(i++);
			cell.setCellValue(col);
			if(col.equalsIgnoreCase("% Yes")  || col.equalsIgnoreCase("Activations") || col.equalsIgnoreCase("Activation %")){
				cell.setCellStyle(styleRED);
			}else{
				cell.setCellStyle(style0);
			}
			
			
			sheet.setColumnWidth(cell.getColumnIndex(), 2500);

		}
		XSSFRow row;
		i = 1;
		for (DailySummaryBean bean : list) {
			j = 0;

			row = sheet.createRow(i);

			cell = row.createCell(j++);
			cell.setCellValue(bean.getPARTNER_NAME());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getPRODUCT_NAME());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getREQUESTS_RECEIVED());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getNO_USER_RESPONSE());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getUSER_YES());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getUSER_NO());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getUSER_NONE());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getUSER_NULL_XY());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getTIME_OUT());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getYES_PERC());
			//cell.setCellStyle(styleRED);
			
			cell = row.createCell(j++);
			cell.setCellValue(bean.getACTIVATIONS());
			//cell.setCellStyle(styleRED);

			cell = row.createCell(j++);
			cell.setCellValue(bean.getACTIVATION_PERC());
			//cell.setCellStyle(styleRED);

			i++;
		}
		return flag;

	}

	public static void main(String[] args) {

		String[] fileName = Constants.fileName;
		
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);
		System.out.println("Writing into Daily Summary work sheet ......");
		FileOutputStream out = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		
		CallableStatement cs = null;
		Connection con = null;
		try {
		//	Properties prop = PropertyClass.getProperty();
			String folderPath =  PropertyClass.getFilePath("");//prop.getProperty("folder_Path");
			
			String DSprocedure = Constants.daily_proc;
			con = DBConnection.getConnection();
			System.out.println("Connection Successful");
			/*ps1 = con.prepareStatement(Constants.daily_delete);
			ps1.execute();*/
			
			System.out.println("Procedure calling .....");
			cs = con.prepareCall(DSprocedure);
			cs.execute();
			System.out.println("Procedure Called!!!");
			List<DailySummaryBean> list = new ArrayList<DailySummaryBean>();

			for (int i = 0; i < fileName.length; i++) {
				System.out
						.println("********************************************************");
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet(fileName[i]
						+ " Daily Summary");
				String sql = Constants.daily_report;

				out = new FileOutputStream(new File(folderPath + fileName[i]
						+ "_Daily_Summary_Report_" + sysdate + ".xlsx"));
				System.out.println("Generating File " + fileName[i]
						+ "_Daily_Summary_Report_" + sysdate + ".xlsx");

				ps = con.prepareStatement(sql);
				ps.setString(1, fileName[i]);
				rs = ps.executeQuery();

				while (rs.next()) {
					DailySummaryBean bean = new DailySummaryBean();

					bean.setPARTNER_NAME(rs.getString("PARTNER_NAME"));
					bean.setPRODUCT_NAME(rs.getString("PRODUCT_NAME"));
					bean.setREQUESTS_RECEIVED(rs.getInt("REQUESTS_RECEIVED"));
					bean.setNO_USER_RESPONSE(rs.getInt("NO_USER_RESPONSE"));
					bean.setUSER_YES(rs.getInt("USER_YES"));
					bean.setUSER_NO((rs.getInt("USER_NO")));
					bean.setUSER_NONE(rs.getInt("USER_NONE"));
					bean.setUSER_NULL_XY(rs.getInt("USER_NULL_XY"));
					bean.setTIME_OUT(rs.getInt("TIME_OUT"));
					bean.setYES_PERC(rs.getString("YES_PERC"));
					bean.setACTIVATIONS(rs.getInt("ACTIVATIONS"));
					bean.setACTIVATION_PERC(rs.getString("ACTIVATION_PERC"));

					list.add(bean);
				}

				boolean flag = false;

				flag = excelFileMerchantAnalysis(out, sheet, workbook, list);
				list.clear();

				workbook.write(out);
				out.close();

				System.out.println("File Generated : " + flag);

			}
			System.out.println("Completed !!!");
		} catch (SQLException e) {
			// TODO: handle exception
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (ps1 != null)
					ps1.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
				
				if (cs != null)
					cs.close();

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
