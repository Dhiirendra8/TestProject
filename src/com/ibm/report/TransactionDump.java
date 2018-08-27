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
import java.util.Properties;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean.TransactionDumpBean;
import com.ibm.dao.DBConnection;
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;

public class TransactionDump  {
	
	
	public static boolean excelFileTransactionDump(FileOutputStream out,
			XSSFSheet sheet, XSSFWorkbook workbook,
			List<TransactionDumpBean> dataList) {
		System.out.println("******Inside excelFileTransactionDump method*****");
		boolean flag = true;
		List<String> list = new ArrayList<String>();
		list.add("CG Trxn Id");
		list.add("Date & Time Stamp");
		list.add("MSISDN");
		list.add("Service Id");
		list.add("Event Id");
		list.add("Merchant Id");
		list.add("Subscription/ PPU");
		list.add("Channel Mode");
		list.add("Consent Mode");
		list.add("API1 Response");
		list.add("API2 Response");
		list.add("Activation Status");

		XSSFCell cell;
		XSSFRow row1 = sheet.createRow(1);

		XSSFCellStyle style0 = workbook.createCellStyle();
		XSSFColor color0 = new XSSFColor(new java.awt.Color(255, 217, 102));
		style0.setFillForegroundColor(color0);
		style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style0.setBorderLeft(BorderStyle.THIN);
		style0.setBorderRight(BorderStyle.THIN);
		style0.setBorderTop(BorderStyle.THIN);
		style0.setBorderBottom(BorderStyle.THIN);
		style0.setWrapText(true);
		style0.setAlignment(HorizontalAlignment.CENTER);

		DataFormat format = workbook.createDataFormat();
		XSSFCellStyle style1 = workbook.createCellStyle();
		style1.setWrapText(true);
		style1.setAlignment(HorizontalAlignment.CENTER);
		style1.setDataFormat(format.getFormat("#"));

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setWrapText(true);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setDataFormat(format.getFormat("dd-MM-yy  h:mm:ss"));

		// style0.setFont(font0);

		int i = 0, j;
		for (String col : list) {
			cell = row1.createCell(i++);
			cell.setCellValue(col);
			cell.setCellStyle(style0);
			sheet.setColumnWidth(cell.getColumnIndex(), 2500);

		}
		System.out.println("After list for loop");
		XSSFRow row;
		i = 2;
		for (TransactionDumpBean bean : dataList) {
			
			
			j = 0;
			row = sheet.createRow(i);

			cell = row.createCell(j++);
			cell.setCellValue(bean.getCG_TRXN_ID());
			sheet.autoSizeColumn(cell.getColumnIndex());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getDATE_TIMESTAMP());
			cell.setCellStyle(style2);
			sheet.autoSizeColumn(cell.getColumnIndex());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getMSISDN());
			cell.setCellStyle(style1);
			sheet.autoSizeColumn(cell.getColumnIndex());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getSERVICE_ID());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getEVENT_ID());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getMERCHANT_ID());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getSUBSCRIPTION());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getCHANNEL_MODE());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getCONSENT_MODE());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getAPI1_RESPONSE_TIME());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getAPI2_RESPONSE_TIME());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getACTIVATION_STATUS());

			i++;
		}
		System.out.println("******Exit excelFileTransactionDump method*****");
		return flag;

	}

	public static void main(String[] args) {
		//public static void mainTransactionDump(){
		System.out.println("Writing in Transaction Dump work sheet ......");
		String[] fileName = Constants.fileName;
		Properties prop = PropertyClass.getProperty();
		String folderPath = PropertyClass.getFilePath("");//prop.getProperty("folder_Path");
		
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);

		FileOutputStream out = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;

		try {
			//String DSprocedure = "{call TRANSACTION_DUMP_PROC(sysdate)}";
			String DSprocedure = Constants.transaction_proc;
			
			con = DBConnection.getConnection();
			System.out.println("Procedure calling .....");
			cs = con.prepareCall(DSprocedure);
			cs.execute();
			System.out.println("Procedure Called!!!");

			List<TransactionDumpBean> list = new ArrayList<TransactionDumpBean>();
			int i = 0;
			// for loop
			for (i = 0; i < fileName.length; i++) {
				System.out
						.println("********************************************************");
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet(fileName[i]
						+ " Transaction Dump");
				//String sql = "select CG_Trxn_Id, Date_TimeStamp , MSISDN , Service_Id , Event_Id ,Merchant_Id ,Subscription ,Channel_Mode ,Consent_Mode ,API1_Response_time ,API2_Response_time ,Activation_Status from transaction_dump where trunc(UPLOAD_DATE)=trunc(sysdate) and CG_flow=? order by CG_Trxn_Id";
				String sql = Constants.transaction_report;
				
				out = new FileOutputStream(new File(folderPath + fileName[i]
						+ "_Transaction_Dump_Report_" + sysdate + ".xlsx"));

				System.out.println("Generating File " + fileName[i]
						+ "_Transaction_Dump_Report_" + sysdate + ".xlsx");

				ps = con.prepareStatement(sql);
				ps.setString(1, fileName[i]);
				rs = ps.executeQuery();
				System.out.println("Query Executed");
				while (rs.next()) {
					TransactionDumpBean bean = new TransactionDumpBean();

					bean.setCG_TRXN_ID(rs.getString("CG_TRXN_ID"));
					bean.setDATE_TIMESTAMP(rs.getTimestamp("DATE_TIMESTAMP"));
					bean.setMSISDN(rs.getLong("MSISDN"));
					bean.setSERVICE_ID(rs.getString("SERVICE_ID"));
					bean.setEVENT_ID(rs.getString("EVENT_ID"));
					bean.setMERCHANT_ID(rs.getString("MERCHANT_ID"));
					bean.setSUBSCRIPTION(rs.getString("SUBSCRIPTION"));
					bean.setCHANNEL_MODE(rs.getString("CHANNEL_MODE"));
					bean.setCONSENT_MODE(rs.getString("CONSENT_MODE"));
					bean.setAPI1_RESPONSE_TIME(rs.getInt("API1_RESPONSE_TIME"));
					bean.setAPI2_RESPONSE_TIME(rs.getInt("API2_RESPONSE_TIME"));
					bean.setACTIVATION_STATUS(rs.getString("ACTIVATION_STATUS"));

					list.add(bean);
				}

				boolean flag = false;
				flag = excelFileTransactionDump(out, sheet, workbook, list);
				list.clear();
				workbook.write(out);
				out.close();

				System.out.println("File Generated : " + flag);

			} // End For loop
			System.out.println("Completed !!!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
