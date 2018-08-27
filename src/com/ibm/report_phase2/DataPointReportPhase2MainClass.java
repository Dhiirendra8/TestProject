package com.ibm.report_phase2;

public class DataPointReportPhase2MainClass {

	public static void main(String[] args) {
		System.out.println();
		System.out.println("****************Data Points Activations Report***************");
		DataPoints_Activations.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API1 Duplicate Requests Report***************");
		DataPoints_API1_DuplicateRequests.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API1 Requests Report***************");
		DataPoints_API1_Request.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API1 Duplicate Timeouts Report***************");
		DataPoints_API1_Timeouts.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API2 No User Response Report***************");
		DataPoints_API2_NoUserResponse.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API2 Requests Report***************");
		DataPoints_API2_Requests.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API2 Consents Report***************");
		DataPoints_Consent.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API2 No Report***************");
		DataPoints_No.main(null);
		System.out.println();
		
		System.out.println("****************Data Points API2 Null XY Report***************");
		DataPoints_NullXY.main(null);
		System.out.println();
		
		System.out.println("***********************************************************************");
		System.out.println("Batch Run Successfully !!!");
	}

}
