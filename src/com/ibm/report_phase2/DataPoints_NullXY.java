package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_NullXY {

	public static void main(String[] args) {		
		boolean flag = false;

		try {
			//String sql2 = Constants.nullXY;
			Constants.setPhase2Column("nvl(NULL_XY,0) as NULL_XY");
			String sql2 = Constants.phase2Query1+"nvl(NULL_XY,0) as NULL_XY"+Constants.phase2Query2;
			String reportName = "API2_Null_XY";
			String header = "API2 - Null XY";
			String sheetName = "Data Points - Null XY";
			String param = "NULL_XY";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
