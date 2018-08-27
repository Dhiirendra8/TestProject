package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_API1_DuplicateRequests {

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.api1_duplicate_request;
			
			Constants.setPhase2Column("nvl(API1_Duplicates,0) as API1_Duplicates");
			String sql2 = Constants.phase2Query1+"nvl(API1_Duplicates,0) as API1_Duplicates"+Constants.phase2Query2;
			String reportName = "API1_Duplicate_Requests";
			String header = "API1 - Duplicate Requests";
			String sheetName = "Data Points - API1 Duplicate Re";
			String param = "API1_Duplicates";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
