package com.ibm.transactionDump;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.ibm.bean.TransactionDumpBean;
import com.ibm.dao.DBConnection;
import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;

public class TransactionMainClass {
	private static boolean fetchAndWriteToXLSX (List<String> merchantList){

		String methodName = " TransactionMainClass :: fetchAndWriteToXLSX :: ";
		System.out.println(methodName+"starts");
		boolean success = false;
		
		try {
			for(int i=0 ; i < merchantList.size(); i++){
				final String x = merchantList.get(i);
				Runnable t = new Runnable(){
					public void run(){
						SchedulerMultiThreadCircleWiseUtility schedulerMultiThreadUtility = new SchedulerMultiThreadCircleWiseUtility();
						schedulerMultiThreadUtility.setMerchatId(x);
						new Thread(schedulerMultiThreadUtility).run();
					}
				};
				new Thread(t).start();
				
			} 
			success = true;
		}catch (Exception e) {
			System.out.println(methodName+"exception found while iterating resultset"+e);
		}
		System.out.println(methodName+"Ends");
		return success;
	}
	public static void main(String[] args) {

		System.out.println("Writing into Transaction Dump work sheet ......");		
		String[] fileName = Constants.fileName;		
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		Connection con = null;
		
		try {
			con = DBConnection.getConnection();			
			for (int i = 0; i < fileName.length; i++) {
				String sql1 = Constants.tran_merchant_ids_2;
				List<String> merchantList = new ArrayList<String>();
				ps1 = con.prepareStatement(sql1);
				ps1.setString(1, fileName[i]);
				rs1 = ps1.executeQuery();
				
				while (rs1.next()) {
					merchantList.add(rs1.getString("MERCHANT_ID"));
				}
				System.out.println("Merchant List : "+merchantList);
				if(fetchAndWriteToXLSX(merchantList)){
					System.out.println("Content writting to the file");
				}
			}		
		
		} catch (Exception e) {			
			e.printStackTrace();
		}finally {
			
			try {
				if (con != null)
					con.close();
				if (ps1 != null)
					ps1.close();
				if (ps != null)
					ps.close();
				

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		
			
		
		
		
	System.out.println("Completed !!!");

	}

}
