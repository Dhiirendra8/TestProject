package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_API2_NoUserResponse {

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.api2_no_user_response;
			Constants.setPhase2Column("nvl(API2_NOUSER_RESPONSE,0) as API2_NOUSER_RESPONSE");
			String sql2 = Constants.phase2Query1+"nvl(API2_NOUSER_RESPONSE,0) as API2_NOUSER_RESPONSE"+Constants.phase2Query2;
			String reportName = "API2_No_User_Response";
			String header = "API2 - No User Response";
			String sheetName = "API2 No User Resp";
			String param = "API2_NOUSER_RESPONSE";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
