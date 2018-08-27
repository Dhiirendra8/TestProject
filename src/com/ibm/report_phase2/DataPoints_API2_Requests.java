package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_API2_Requests {

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.api2_request;
			Constants.setPhase2Column("nvl(API2_Requests,0) as API2_Requests");
			String sql2 = Constants.phase2Query1+"nvl(API2_Requests,0) as API2_Requests"+Constants.phase2Query2;
			String reportName = "API2_Requests";
			String header = "API2 Requests";
			String sheetName = "Data Points - API2 Req";
			String param = "API2_Requests";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
