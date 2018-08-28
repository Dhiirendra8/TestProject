package com.ibm.util;

public class Constants {

	public static final String currentMonth = PropertyClass.getProperty().getProperty("currentMonth");
	public static String phase2Column;
	
	
	// local
	 
	

	public static final String prop_file = "C:/CG_Dynamic_Reports/prop_files/resources.properties";
	
	// SIT 242 server
	//public static final String prop_file = "E:/CG_Dynamic_Reports/prop_files/resources.properties";
	
	// SIT 13 server
	// public static final String prop_file ="/home/ITMrutuj/DM16958_CGReport/prop_files/resources.properties";
	 
	 //Production
	 //public static final String prop_file = "C:/CG_Dynamic_Reports/prop_files/resources.properties";

	public static String getPhase2Column() {
		return phase2Column;
	}

	public static void setPhase2Column(String phase2Column) {
		Constants.phase2Column = phase2Column;
	}

	// ********************************************************************
	public static final String[] fileName = { "WAP", "PPD" };

	// Merchant Analysis
	public static final String merchant_delete = "delete from MERCHANT_ANALYSIS where trunc(upload_date)=trunc(sysdate)";
	public static final String merchant_proc = "{call MERCHANT_ANALYSIS_PROC(sysdate-1)}";
	public static final String merchant_ids = "select distinct(MERCHANT_ID) from merchant_analysis where CG_FLOW=? and to_char(upload_date,'MON')=to_char(sysdate,'MON') order by MERCHANT_ID";
	public static final String merchant_report = "select UPLOAD_DATE, MERCHANT_ID, API1_REQUESTS, API1_DUPLICATES, API1_TIMEOUTS, API2_REQUESTS, API2_NOUSER_RESPONSE, CONSENTS, API2_NO, NULL_XY, ACTIVATIONS, API1_PERC_DUPLICATES, API1_PERC_TIMEOUTS,API2_PERC_REQUESTS, NULL_PERC_XY, CONSENT_PERC_API1,ACTIVATIONS_PERC_TO_CONSENT from merchant_analysis where MERCHANT_ID = ? and CG_FLOW=? and to_char(upload_date,'MON')=to_char(sysdate,'MON')";

	// Daily Summary
	public static final String daily_delete = "delete from DAILY_SUMMARY where trunc(upload_date)=trunc(sysdate)";
	public static final String daily_proc = "{call Daily_Summary_proc(sysdate-1)}";
	public static final String daily_report = "select Partner_name ,Product_Name,Requests_Received ,No_User_Response ,User_Yes ,user_No ,user_None ,user_Null_xy ,Time_Out ,Yes_perc ,Activations ,Activation_perc from daily_summary where trunc(UPLOAD_DATE)=trunc(sysdate) and CG_flow=? order by Partner_name ,Product_Name";

	
	
	// Transaction Dump
	public static final String transaction_delete = "delete from transaction_dump where trunc(upload_date)=trunc(sysdate)";
	public static final String transaction_proc = "{call TRANSACTION_DUMP_PROC(sysdate-1)}";
	public static final String tran_merchant_ids = "select distinct(MERCHANT_ID) from transaction_dump where CG_FLOW=? and MERCHANT_ID is not null and to_char(upload_date,'MON')=to_char(sysdate,'MON') and trunc(UPLOAD_DATE)=trunc(sysdate) ";
	public static final String transaction_report = "select CG_Trxn_Id, Date_TimeStamp , MSISDN , Service_Id , Event_Id ,Merchant_Id ,Subscription ,Channel_Mode ,Consent_Mode ,API1_Response_time ,API2_Response_time ,Activation_Status from transaction_dump where trunc(UPLOAD_DATE)=trunc(sysdate) and CG_flow=? and Merchant_Id = ? order by CG_Trxn_Id";
	public static final String transaction_report_2 = "select CG_Trxn_Id, Date_TimeStamp , MSISDN , Service_Id , Event_Id ,Merchant_Id ,Subscription ,Channel_Mode ,Consent_Mode ,API1_Response_time ,API2_Response_time ,Activation_Status from transaction_dump where  Merchant_Id = ?";
	public static final String tran_merchant_ids_2 = "select distinct(MERCHANT_ID) from transaction_dump where CG_FLOW=? and MERCHANT_ID is not null ";
	// *******************Data Points**************************************

	// API1 Request
	public static final String phase2Query1 = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, ";
	public static final String phase2Query2 = " from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";
	
	public static final String merchant_ids_phase2 = "select distinct(MERCHANT_ID) from merchant_analysis where CG_FLOW=? and to_char(upload_date,'MON')="+currentMonth +"order by MERCHANT_ID";
	
	/*public static final String api1_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID,API1_REQUESTS from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String api1_timeouts = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API1_Timeouts,0) as API1_Timeouts from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String api1_duplicate_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API1_Duplicates,0) as API1_Duplicates from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String api2_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_Requests,0) as API2_Requests from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String api2_no_user_response = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_NOUSER_RESPONSE,0) as API2_NOUSER_RESPONSE from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String consents = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(CONSENTS,0) as CONSENTS from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String api2_no = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_NO,0) as API2_NO from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String nullXY = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(NULL_XY,0) as NULL_XY from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";

	public static final String activations = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(ACTIVATIONS,0) as ACTIVATIONS from MERCHANT_ANALYSIS "
			+ "where to_char(upload_date,'MON')="+currentMonth +" and CG_FLOW=? order by UPLOAD_DATE";*/

	
	//Production
	/*// Merchant Analysis
		public static final String merchant_delete = "delete from cgprod.MERCHANT_ANALYSIS where trunc(upload_date)=trunc(sysdate)";
		public static final String merchant_proc = "{call cgprod.MERCHANT_ANALYSIS_PROC(sysdate-1)}";
		public static final String merchant_ids = "select distinct(MERCHANT_ID) from cgprod.MERCHANT_ANALYSIS where CG_FLOW=? and to_char(upload_date,'MON')=to_char(sysdate,'MON') order by MERCHANT_ID";
		public static final String merchant_report = "select UPLOAD_DATE, MERCHANT_ID, API1_REQUESTS, API1_DUPLICATES, API1_TIMEOUTS, API2_REQUESTS, API2_NOUSER_RESPONSE, CONSENTS, API2_NO, NULL_XY, ACTIVATIONS, API1_PERC_DUPLICATES, API1_PERC_TIMEOUTS,API2_PERC_REQUESTS, NULL_PERC_XY, CONSENT_PERC_API1,ACTIVATIONS_PERC_TO_CONSENT from cgprod.MERCHANT_ANALYSIS where MERCHANT_ID = ? and CG_FLOW=? and to_char(upload_date,'MON')=to_char(sysdate,'MON')";

		// Daily Summary
		public static final String daily_delete = "delete from cgprod.DAILY_SUMMARY where trunc(upload_date)=trunc(sysdate)";
		public static final String daily_proc = "{call cgprod.DAILY_SUMMARY_proc(sysdate-1)}";
		public static final String daily_report = "select Partner_name ,Product_Name,Requests_Received ,No_User_Response ,User_Yes ,user_No ,user_None ,user_Null_xy ,Time_Out ,Yes_perc ,Activations ,Activation_perc from cgprod.DAILY_SUMMARY where trunc(UPLOAD_DATE)=trunc(sysdate) and CG_flow=? order by Partner_name ,Product_Name";

		// Transaction Dump
		public static final String transaction_delete = "delete from cgprod.transaction_dump where trunc(upload_date)=trunc(sysdate)";
		public static final String transaction_proc = "{call cgprod.transaction_dump_PROC(sysdate-1)}";
		public static final String tran_merchant_ids = "select distinct(MERCHANT_ID) from cgprod.transaction_dump where CG_FLOW=? and MERCHANT_ID is not null and to_char(upload_date,'MON')=to_char(sysdate,'MON') and trunc(UPLOAD_DATE)=trunc(sysdate) ";
		public static final String transaction_report = "select CG_Trxn_Id, Date_TimeStamp , MSISDN , Service_Id , Event_Id ,Merchant_Id ,Subscription ,Channel_Mode ,Consent_Mode ,API1_Response_time ,API2_Response_time ,Activation_Status from cgprod.transaction_dump where trunc(UPLOAD_DATE)=trunc(sysdate) and CG_flow=? and Merchant_Id = ? order by CG_Trxn_Id";

		// ******************* Data Points **************************************

		// API1 Request
		public static final String api1_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID,API1_REQUESTS from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String api1_timeouts = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API1_Timeouts,0) as API1_Timeouts from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String api1_duplicate_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API1_Duplicates,0) as API1_Duplicates from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String api2_request = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_Requests,0) as API2_Requests from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String api2_no_user_response = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_NOUSER_RESPONSE,0) as API2_NOUSER_RESPONSE from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String consents = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(CONSENTS,0) as CONSENTS from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String api2_no = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(API2_NO,0) as API2_NO from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String nullXY = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(NULL_XY,0) as NULL_XY from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";

		public static final String activations = "select trunc(UPLOAD_DATE) as UPLOAD_DATE ,MERCHANT_ID, nvl(ACTIVATIONS,0) as ACTIVATIONS from cgprod.MERCHANT_ANALYSIS "
				+ "where to_char(upload_date,'MON')=to_char(sysdate,'MON') and CG_FLOW=? order by UPLOAD_DATE";
*/
}
