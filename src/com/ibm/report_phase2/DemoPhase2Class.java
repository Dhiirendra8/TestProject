package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DemoPhase2Class {

	static String[] columns = { "nvl(ACTIVATIONS,0) as ACTIVATIONS", "nvl(API1_Duplicates,0) as API1_Duplicates",
			"API1_REQUESTS", "nvl(API1_Timeouts,0) as API1_Timeouts",
			"nvl(API2_NOUSER_RESPONSE,0) as API2_NOUSER_RESPONSE", "nvl(API2_Requests,0) as API2_Requests",
			"nvl(CONSENTS,0) as CONSENTS", "nvl(API2_NO,0) as API2_NO", "nvl(NULL_XY,0) as NULL_XY" };
	static String[] reportName = { "VAS_Activations", "API1_Duplicate_Requests", "API1_Requests", "API1_Timeouts",
			"API2_No_User_Response", "API2_Requests", "API2_Consents", "API2 - No", "API2_Null_XY" };
	static String[] header = { "VAS Activations", "API1 - Duplicate Requests", "API1 Requests", "API 1 Time Outs",
			"API2 - No User Response", "API2 Requests", "API2 - Consents", "API2 - No", "API2 - Null XY" };
	static String[] sheetName = { "Data Points - Activations", "Data Points - API1 Duplicate Re",
			"Data Points - API1 Req", "Data Points - API1 Timeouts", "API2 No User Resp", "Data Points - API2 Req",
			"Data Points - Consent", "Data Points - No", "Data Points - Null XY" };
	static String[] param = { "ACTIVATIONS", "API1_Duplicates", "API1_REQUESTS", "API1_TIMEOUTS",
			"API2_NOUSER_RESPONSE", "API2_Requests", "CONSENTS", "API2_NO", "NULL_XY" };

	public static void main(String[] args) {
		System.out.println(columns.length + " "+reportName.length+" "+ header.length+" "+sheetName.length+" "+param.length);
		for (int i = 0; i < 9; i++) {
			boolean flag = false;
			String sql2 = Constants.phase2Query1 + columns[i] + Constants.phase2Query2;
			System.out.println("******************  "+reportName[i]+"  **********************************");
			flag = GenarateExcelClass.generateTemplate(sql2, reportName[i], header[i], sheetName[i], param[i]);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		}
		System.out.println("****************************************************\nBatch Run Successfully !!!");
	}

}
