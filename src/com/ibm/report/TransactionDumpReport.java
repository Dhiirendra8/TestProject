package com.ibm.report;

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

public class TransactionDumpReport {

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
			
			String DSprocedure = Constants.transaction_proc;
			con = DBConnection.getConnection();
			System.out.println("Connection Successful");
			/*ps = con.prepareStatement(Constants.transaction_delete);
			ps.execute();*/
			System.out.println("Procedure calling .....");
			cs = con.prepareCall(DSprocedure);
			cs.execute();

			System.out.println("Procedure Executed!!!");

			int i = 0;
			
			for (i = 0; i < fileName.length; i++) {
				String sql1 = Constants.tran_merchant_ids;
				List<String> merchantList = new ArrayList<String>();
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, fileName[i]);
				rs1 = ps1.executeQuery();

				while (rs1.next()) {
					merchantList.add(rs1.getString("MERCHANT_ID"));
				}
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = null;
				out = new FileOutputStream(new File(folderPath
						+ fileName[i] + "_Transaction_Dump_Report_"
						+ sysdate + ".xlsx"));
				if (merchantList.isEmpty()) {
					System.out.println("No Data Found ...");
					sheet = workbook.createSheet("No Data");
					flag = excelFileTransactionDump(out, sheet, workbook,
							null);
					workbook.write(out);
					out.close();
				} else {
					
					System.out.println("Writing into " + folderPath
							+ fileName[i] + "_Transaction_Dump_Report_"
							+ sysdate + ".xlsx");
					for (String merchant : merchantList) {

						sheet = workbook.createSheet(fileName[i] + "_"
								+ merchant);
						List<TransactionDumpBean> dataList = new ArrayList<TransactionDumpBean>();

						String sql2 = Constants.transaction_report;
						ps2 = con.prepareStatement(sql2);
						ps2.setString(1, fileName[i]);
						ps2.setString(2, merchant);
						rs2 = ps2.executeQuery();

						while (rs2.next()) {
							TransactionDumpBean bean = new TransactionDumpBean();

							bean.setCG_TRXN_ID(rs2.getString("CG_TRXN_ID"));
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
						flag = excelFileTransactionDump(out, sheet, workbook,
								dataList);
						dataList.clear();

					}
					workbook.write(out);
					out.close();
					System.out.println("File Generated : " + flag);
				}
			}
			out.close();
		
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
