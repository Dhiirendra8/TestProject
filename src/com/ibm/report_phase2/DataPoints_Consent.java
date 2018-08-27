package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_Consent {


	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.consents;
			Constants.setPhase2Column("nvl(CONSENTS,0) as CONSENTS");
			String sql2 = Constants.phase2Query1+"nvl(CONSENTS,0) as CONSENTS"+Constants.phase2Query2;
			String reportName = "API2_Consents";
			String header = "API2 - Consents";
			String sheetName = "Data Points - Consent";
			String param = "CONSENTS";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
