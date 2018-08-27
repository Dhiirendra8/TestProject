package com.ibm.report_phase2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean_phase2.DataPointsPhase2Bean;
import com.ibm.dao.DBConnection;
import com.ibm.util.Constants;

public class DataPoints_API1_Request {
	
	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 =Constants.api1_request;
			Constants.setPhase2Column("API1_REQUESTS");
			String sql2 = Constants.phase2Query1+"API1_REQUESTS"+Constants.phase2Query2;
			String reportName = "API1_Requests";
			String header = "API1 Requests";
			String sheetName = "Data Points - API1 Req";
			String param = "API1_REQUESTS";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main1(String[] args) {/*

		boolean flag = false;
		String[] fileName = Constants.fileName;
		String reportName = "API1 Requests";
		String header = "API1 Requests";
		String sheetName = "Data Points - API1 Req";
		//HashMap<Date, List<List<DataPointsAPI1RequestBean>>> map = null;
		List<String> merchantList = null;
		String sql1 = Constants.merchant_ids;
		//String sql2 =Constants.api1_request;
		
		Connection con = null;
		PreparedStatement ps1 =null;
		PreparedStatement ps2 =null;
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
			//for loop for wap & ppd
			for (int i = 0; i < fileName.length; i++) {
			sheet = workbook.createSheet(fileName[i]+" "+sheetName);
			ps1 = con.prepareStatement(sql1);
			ps1.setString(1, fileName[i]);
			rs1 = ps1.executeQuery();
			merchantList = new ArrayList<String>();
			while(rs1.next()) {
				merchantList.add(rs1.getString("MERCHANT_ID"));
			}
			System.out.println(merchantList);			
			
			ps2 = con.prepareStatement(sql2);
			ps2.setString(1, fileName[i]);
			rs2 = ps2.executeQuery();
			
			List<DataPointsAPI1RequestBean> list = new ArrayList<DataPointsAPI1RequestBean>();
			while(rs2.next()) {
				DataPointsAPI1RequestBean bean = new DataPointsAPI1RequestBean();
				bean.setMerchantId(rs2.getString("MERCHANT_ID"));
				bean.setUploadDate(rs2.getDate("UPLOAD_DATE"));
				bean.setCount(rs2.getLong("API1_REQUESTS"));
				
				list.add(bean);
			}
			
			
			
			flag = GenarateExcelClass.generateExcel(reportName, header, list, merchantList,sheet, workbook);
			System.out.println("File Generated : " + flag);
			}
			//End For loop
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	*/}

}
