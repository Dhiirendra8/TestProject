package com.ibm.transactionDump;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.ibm.bean.TransactionDumpBean;
import com.ibm.dao.DBConnection;
import com.ibm.report.TransactionDump;
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;


public class SchedulerMultiThreadCircleWiseUtility implements Runnable{

	//public static ResourceBundle rb = ResourceBundle.getBundle("com.ibm.transactionDump.scheduler");
	BufferedWriter bwriter = null;
	Connection conn = null;
	FileOutputStream out;
	String merchatId ;
	
	public String getMerchatId() {
		return merchatId;
	}

	public void setMerchatId(String merchatId) {
		this.merchatId = merchatId;
	}

	@Override
	public void run() {

		String methodName = " SchedulerMultiThreadCircleWiseUtility Thread : ";
		ExecutorService executor = null;
		Future<String> future = null;
		try {
			System.out.println(methodName+"Started");
			System.out.println("getting merchant wise dump");
			executor = Executors.newSingleThreadExecutor();
			future = executor.submit(new Task());
			String threadTimeOut = "3";
			int timeOut = 3;
			if(null != threadTimeOut){
				timeOut = Integer.parseInt(threadTimeOut);
			}
			future.get(timeOut, TimeUnit.HOURS);
			System.out.println(methodName+"Finished");
		} catch (Exception e) {
			System.out.println(methodName+" Terminated : "+e.getMessage());
		}finally{
			if(null != executor){
				
				if(!future.isDone()){
				
					try {
						if(null!=bwriter){
							bwriter.close();
							bwriter = null;
						}
						if(null != conn){
							conn.close();
							conn = null;
						}
					} catch (IOException e) {
					
						e.printStackTrace();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					catch (Exception e) {
					
						e.printStackTrace();
					}
					future.cancel(true);
					System.out.println(methodName+" task is cancelled manually ");
				}
				executor.shutdownNow();
			//	System.out.println(methodName+" executer shut down end ");
			}
		}
	}
	class Task implements Callable<String> {
		@Override
		public String call() throws Exception {

			String methodName = " write in file :: ";
			System.out.println(methodName+"starts");
			PreparedStatement ps = null;
			ResultSet rs = null;
		
			
			try {
				if(null == conn){
					conn = DBConnection.getConnection();
				}
				
				
				
				String folderPath = PropertyClass.getFilePath("/Transaction_Dump/");
				System.out.println("Folder Path : "+folderPath);
				List<TransactionDumpBean> dataList = new ArrayList<TransactionDumpBean>();
				dataList.clear();
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet(merchatId);
			    out = new FileOutputStream(new File(folderPath
						 +merchatId+ "_Transaction_Dump_Report_"
						 + ".xlsx"));

				String sql2 = Constants.transaction_report_2;
				ps = conn.prepareStatement(sql2);
				ps.setString(1, merchatId);
				rs = ps.executeQuery();
				
				while (rs.next()) {
					TransactionDumpBean bean = new TransactionDumpBean();

					bean.setCG_TRXN_ID(rs.getString("CG_TRXN_ID"));
					//System.out.println(rs2.getString("CG_TRXN_ID"));
					bean.setDATE_TIMESTAMP(rs
							.getTimestamp("DATE_TIMESTAMP"));
					bean.setMSISDN(rs.getLong("MSISDN"));
					bean.setSERVICE_ID(rs.getString("SERVICE_ID"));
					bean.setEVENT_ID(rs.getString("EVENT_ID"));
					bean.setMERCHANT_ID(rs.getString("MERCHANT_ID"));
					bean.setSUBSCRIPTION(rs.getString("SUBSCRIPTION"));
					bean.setCHANNEL_MODE(rs.getString("CHANNEL_MODE"));
					bean.setCONSENT_MODE(rs.getString("CONSENT_MODE"));
					bean.setAPI1_RESPONSE_TIME(rs
							.getInt("API1_RESPONSE_TIME"));
					bean.setAPI2_RESPONSE_TIME(rs
							.getInt("API2_RESPONSE_TIME"));
					bean.setACTIVATION_STATUS(rs
							.getString("ACTIVATION_STATUS"));

					dataList.add(bean);
					
				}
			
				boolean flag = excelFileTransactionDump(out, sheet, workbook, dataList);
				System.out.println("Flag : "+flag);

			workbook.write(out);
				
					
					
					
				
			}catch (SQLException e) {
				System.out.println(methodName+"exception found while iterating resultset"+e);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println(methodName+"Unable to write to the file"+e);
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(methodName+" : Exception found while write to file : "+e);
				e.printStackTrace();
			}
			finally{
				try {
					if(null != conn)
						conn.close();
					if(null != bwriter){
						bwriter.close();	
					}
					if (ps != null)
						ps.close();
					if (rs != null)
						rs.close();
					if (out != null)
						out.close();
					
				} catch (SQLException e) {
					System.out.println("Exception while close connection"+e);
				} catch (IOException e) {
					System.out.println("Exception while close IO"+e);
				}
			}
			System.out.println(methodName+" Ends");
			return "";
		}
	}

	public static boolean excelFileTransactionDump(FileOutputStream out,
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
		//System.out.println(dataList.size());
		if(dataList != null) {
			for (TransactionDumpBean bean : dataList) {

				j = 0;
				row = sheet.createRow(i);

				cell = row.createCell(j++);
				cell.setCellValue(bean.getCG_TRXN_ID());
				// sheet.autoSizeColumn(cell.getColumnIndex());

				cell = row.createCell(j++);
				cell.setCellValue(bean.getDATE_TIMESTAMP());
				//cell.setCellStyle(style2);
				//sheet.autoSizeColumn(cell.getColumnIndex());

				cell = row.createCell(j++);
				cell.setCellValue(bean.getMSISDN());
				//cell.setCellStyle(style1);
				// sheet.autoSizeColumn(cell.getColumnIndex());

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
			
		}else {
			System.out.println("In else part");
			j = 0;
			row = sheet.createRow(i);

			cell = row.createCell(j);
			cell.setCellValue("No data found");
			sheet.addMergedRegion(new CellRangeAddress(i, i, j, list.size()));
		}
		
		System.out.println("******Exit excelFileTransactionDump method*****");
		return flag;

	}
	


}
