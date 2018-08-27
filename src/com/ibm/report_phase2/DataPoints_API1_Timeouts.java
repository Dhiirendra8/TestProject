package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_API1_Timeouts {

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.api1_timeouts;
			Constants.setPhase2Column("nvl(API1_Timeouts,0) as API1_Timeouts");
			String sql2 = Constants.phase2Query1+"nvl(API1_Timeouts,0) as API1_Timeouts"+Constants.phase2Query2;
			String reportName = "API1_Timeouts";
			String header = "API 1 Time Outs";
			String sheetName = "Data Points - API1 Timeouts";
			String param = "API1_TIMEOUTS";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
