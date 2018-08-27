package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_No {

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.api2_no;
			Constants.setPhase2Column("nvl(API2_NO,0) as API2_NO");
			String sql2 = Constants.phase2Query1+"nvl(API2_NO,0) as API2_NO"+Constants.phase2Query2;
			String reportName = "API2_No";
			String header = "API2 - No";
			String sheetName = "Data Points - No";
			String param = "API2_NO";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
