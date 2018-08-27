package com.ibm.transactionCountWise;

import java.io.File;
import java.io.FileOutputStream;
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
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;

public class TransactionCount extends Thread{
	FileOutputStream out;
	XSSFSheet sheet;
	XSSFWorkbook workbook;
	List<TransactionDumpBean> dataList;
	
		public TransactionCount(FileOutputStream out, XSSFSheet sheet, XSSFWorkbook workbook,
			List<TransactionDumpBean> dataList) {
		super();
		this.out = out;
		this.sheet = sheet;
		this.workbook = workbook;
		this.dataList = dataList;
	}

		public void run() {
			excelFileTransactionDump(out,sheet,workbook,dataList);
		}

	public static synchronized boolean excelFileTransactionDump(FileOutputStream out,
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

	public static void main(String[] args) {

		System.out.println("Writing into Transaction Dump work sheet ......");
		boolean flag = false;
		String[] fileName = Constants.fileName;
		

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);
		XSSFWorkbook workbook = null ;
		FileOutputStream out = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		CallableStatement cs = null;
		Connection con = null;
		long start=0;
		long end = 5000;
		try {		
			String folderPath = PropertyClass.getFilePath("");
			con = DBConnection.getConnection();
			int i = 0;
			
			for (i = 0; i < fileName.length; i++) {
				int sheetCount = 0;
				 start=0;
				 end = 5000;
				String sql1 = Constants.tran_merchant_ids;
				
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, fileName[i]);
				rs1 = ps1.executeQuery();
				
				 workbook = new XSSFWorkbook();
				XSSFSheet sheet = null;
				
			
					
					System.out.println("Writing into " + folderPath
							+ fileName[i] + "_Transaction_Dump_Report_"
							+ sysdate + ".xlsx");
					
						out = new FileOutputStream(new File(folderPath
								+ fileName[i] + "_Transaction_Dump_Report_"
								+ sysdate+"_" + ".xlsx"));
						List<TransactionDumpBean> dataList = new ArrayList<TransactionDumpBean>();
						dataList.clear();
						
						

						
						while(start<end) {
							sheet = workbook.createSheet(fileName[i] + "_"
									+ sheetCount++);
							String sql2 = "select CG_TRXN_ID,DATE_TIMESTAMP,MSISDN,SERVICE_ID,EVENT_ID,MERCHANT_ID,SUBSCRIPTION,CHANNEL_MODE,CONSENT_MODE,API1_RESPONSE_TIME,API2_RESPONSE_TIME," + 
									"ACTIVATION_STATUS" + 
									" from (" + 
									"select CG_TRXN_ID,DATE_TIMESTAMP,MSISDN,SERVICE_ID,EVENT_ID,MERCHANT_ID,SUBSCRIPTION,CHANNEL_MODE,CONSENT_MODE,API1_RESPONSE_TIME,API2_RESPONSE_TIME,ACTIVATION_STATUS," + 
									"row_number() OVER (order by CG_TRXN_ID asc) rn from TRANSACTION_DUMP where CG_FLOW='"+fileName[i]+"') a " + 
									"where rn between "+ start +" and "+ end;
							
							System.out.println(sql2);
							ps2 = con.prepareStatement(sql2);
							
							
							
							rs2 = ps2.executeQuery();
							while (rs2.next()) {
								TransactionDumpBean bean = new TransactionDumpBean();

								bean.setCG_TRXN_ID(rs2.getString("CG_TRXN_ID"));
								//System.out.println(rs2.getString("CG_TRXN_ID"));
								bean.setDATE_TIMESTAMP(rs2
										.getTimestamp("DATE_TIMESTAMP"));
								bean.setMSISDN(rs2.getLong("MSISDN"));
								bean.setSERVICE_ID(rs2.getString("SERVICE_ID"));
								bean.setEVENT_ID(rs2.getString("EVENT_ID"));
								bean.setMERCHANT_ID(rs2.getString("MERCHANT_ID"));
								bean.setSUBSCRIPTION(rs2.getString("SUBSCRIPTION"));
								bean.setCHANNEL_MODE(rs2.getString("CHANNEL_MODE"));
								bean.setCONSENT_MODE(rs2.getString("CONSENT_MODE"));
								bean.setAPI1_RESPONSE_TIME(rs2
										.getInt("API1_RESPONSE_TIME"));
								bean.setAPI2_RESPONSE_TIME(rs2
										.getInt("API2_RESPONSE_TIME"));
								bean.setACTIVATION_STATUS(rs2
										.getString("ACTIVATION_STATUS"));

								dataList.add(bean);
								
							}
							if(dataList.size()>0) {
								
								start = end+1;
								end = end+5000;
								
								//new TransactionCount(out,sheet,workbook,dataList).start();
								excelFileTransactionDump(out,sheet,workbook,dataList);
							}else {
								workbook.removeSheetAt(sheetCount-1);
								start = 0;
								end = 0;
							}
								
							
							dataList.clear();

						
						
						System.out.println("File Generated : " + flag);
					
				
					
						}
						workbook.write(out);
						out.close();
						
			}
			
		
		} catch (Exception e) {
			
			
			e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		
			
		
		
		System.out.println("Completed !!!");
	
	}

}
