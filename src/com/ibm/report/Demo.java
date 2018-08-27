package com.ibm.report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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





public class Demo {
	static XSSFWorkbook workbook = new XSSFWorkbook();
	
	private static boolean excelFileTransactionDump(FileOutputStream out,
			XSSFSheet sheet, XSSFWorkbook workbook,
			List<TransactionDumpBean> dataList) {
		
		
		
		
		
		System.out.println("******Inside excelFileTransactionDump method*****");
		if(dataList!=null)
		System.out.println("Data List size : " + dataList.size());
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
		if(dataList!=null)
		for (TransactionDumpBean bean : dataList) {

			j = 0;
			row = sheet.createRow(i);

			cell = row.createCell(j++);
			cell.setCellValue(bean.getCG_TRXN_ID());
			// sheet.autoSizeColumn(cell.getColumnIndex());

			cell = row.createCell(j++);
			cell.setCellValue(bean.getDATE_TIMESTAMP());
			

			cell = row.createCell(j++);
			cell.setCellValue(bean.getMSISDN());
			

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
	public static void main(String[] args) throws IOException {
		System.out.println("Writing into Transaction Dump work sheet ......");
		boolean flag = false;
		String[] fileName = Constants.fileName;
		

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);

		FileOutputStream out = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		CallableStatement cs = null;
		Connection con = null;
		
		try {
			//Properties prop = PropertyClass.getProperty();
			String folderPath = PropertyClass.getFilePath("");//prop.getProperty("folder_Path");
			
			con = DBConnection.getConnection();
			System.out.println("Connection Successful");
			/*ps = con.prepareStatement(Constants.transaction_delete);
			ps.execute();*/
			//System.out.println("Procedure calling .....");
			

			int i = 0;
			
				
				XSSFSheet sheet = null;
				out = new FileOutputStream(new File(folderPath
						+ fileName[i] + "_Transaction_Dump_Report_"
						+ sysdate + ".xlsx"));
				
					
					System.out.println("Writing into " + folderPath
							+ fileName[i] + "_Transaction_Dump_Report_"
							+ sysdate + ".xlsx");
					

						
						List<TransactionDumpBean> dataList = new ArrayList<TransactionDumpBean>();

						String sql2 = "SELECT CG_Trxn_Id, Date_TimeStamp , MSISDN , Service_Id , Event_Id ,Merchant_Id ,Subscription ,Channel_Mode ,Consent_Mode ,API1_Response_time ,API2_Response_time ,Activation_Status from transaction_dump order by CG_Trxn_Id";
						ps2 = con.prepareStatement(sql2);
						
						
						System.out.println("******Inside excelFileTransactionDump method*****");
						for(int k=0;k<10000;k++) {
							rs2 = ps2.executeQuery();
							System.out.println("i : "+i);
							sheet = workbook.createSheet("Transaction Dump "+k
									);
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

						i = 0;
						int j;
						for (String col : list) {
							cell = row1.createCell(i++);
							cell.setCellValue(col);
							cell.setCellStyle(style0);
							sheet.setColumnWidth(cell.getColumnIndex(), 2500);

						}
						System.out.println("After list for loop");
						XSSFRow row;
						i = 2;
						
						while (rs2.next()) {
							
							j = 0;
							row = sheet.createRow(i);

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("CG_TRXN_ID"));
							// sheet.autoSizeColumn(cell.getColumnIndex());

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getTimestamp("DATE_TIMESTAMP"));
							

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getLong("MSISDN"));
							

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("SERVICE_ID"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("EVENT_ID"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("MERCHANT_ID"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("SUBSCRIPTION"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("CHANNEL_MODE"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("CONSENT_MODE"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getInt("API1_RESPONSE_TIME"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getInt("API2_RESPONSE_TIME"));

							cell = row.createCell(j++);
							cell.setCellValue(rs2.getString("ACTIVATION_STATUS"));

							i++;
							
						

						}
						
						}
					
					workbook.write(out);
					out.close();
					System.out.println("File Generated : " + flag);
				
			
			out.close();
		
		} catch (Exception e) {
			e.printStackTrace();
			workbook.write(out);
			out.close();
			System.out.println("File Generated : " + false);
		}finally {
			try {
				if (con != null)
					con.close();
				if (ps1 != null)
					ps1.close();
				if (ps != null)
					ps.close();
							
				if (cs != null)
					cs.close();

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}

		}


		System.out.println("Completed !!!");
	}
	
	
	/*public static void main(String[] args)  {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyy");
		Date date = new Date();
		String strDate = formatter.format(date);
		 System.out.println(strDate);

		 File file = new File("C:\\CG_Dynamic_Reports\\Reports\\"+strDate+"");
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }else {
	        	System.out.println("Directory Exists");
	        }
		 
		//MerchantReport.mainMerchantAnalysis(strDate);
		//DailySummary.mainDailySummary(strDate);
		//TransactionDump.mainTransactionDump(strDate);
		
		
//		Thread t1=new MerchantAnalysisReport();
//		Thread t2=new DailySummary();
//		Thread t3=new TransactionDump();
//		
//		t1.start();		
//		t2.start();
//		t3.start();
		
		List<Date> dateList = new ArrayList<Date>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int myMonth = cal.get(Calendar.MONTH);

		while (myMonth == cal.get(Calendar.MONTH)) {
			dateList.add(cal.getTime());
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		Date st = dateList.get(0);
		Date ed = dateList.get(dateList.size()-1);
		String start_Date = formatter.format(st);
		String end_Date = formatter.format(ed);
		
		System.out.println(start_Date);
		System.out.println(end_Date);
		

	}*/

}
