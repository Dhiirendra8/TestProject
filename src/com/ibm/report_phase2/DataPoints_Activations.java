package com.ibm.report_phase2;

import com.ibm.util.Constants;

public class DataPoints_Activations extends Constants{

	public static void main(String[] args) {
		boolean flag = false;

		try {
			//String sql2 = Constants.activations;
			
			phase2Column = "nvl(ACTIVATIONS,0) as ACTIVATIONS";
			String sql2 = Constants.phase2Query1+"nvl(ACTIVATIONS,0) as ACTIVATIONS"+Constants.phase2Query2;
			System.out.println(sql2);
			String reportName = "VAS_Activations";
			String header = "VAS Activations";
			String sheetName = "Data Points - Activations";
			String param = "ACTIVATIONS";
			flag = GenarateExcelClass.generateTemplate(sql2, reportName, header, sheetName, param);
			System.out.println("!!! Process Completed : " + flag + " !!!");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
