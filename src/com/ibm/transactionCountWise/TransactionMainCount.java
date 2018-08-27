package com.ibm.transactionCountWise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean.TransactionDumpBean;
import com.ibm.dao.DBConnection;

import com.ibm.util.Constants;
import com.ibm.util.PropertyClass;

public class TransactionMainCount {

	
	/*private static boolean fetchAndWriteToXLSX (XSSFSheet sheet,ConcurrentHashMap<Integer, List<TransactionDumpBean>> map,Integer key,XSSFWorkbook workbook, FileOutputStream out){

		String methodName = " TransactionMainClass :: fetchAndWriteToXLSX :: ";
		System.out.println(methodName+"starts");
		boolean success = false;
		
		try {
			
				Runnable t = new Runnable(){
					public void run(){
						 
						SchedulerMultiThreadUtility schedulerMultiThreadUtility = new SchedulerMultiThreadUtility(map,sheet,workbook,key,out);
						//schedulerMultiThreadUtility.setSheet(sheet);
						System.out.println("DataList in fetchAndWriteToXLSX : "+map.get(key).size());
						schedulerMultiThreadUtility.setDataList(dataList);
						schedulerMultiThreadUtility.setWorkbook(workbook);
						schedulerMultiThreadUtility.setOut(out);
						schedulerMultiThreadUtility.setMap(map);
						new Thread(schedulerMultiThreadUtility).run();
					}
				};
				new Thread(t).start();
				
			 
			success = true;
		}catch (Exception e) {
			System.out.println(methodName+"exception found while iterating resultset"+e);
		}
		System.out.println(methodName+"Ends");
		return success;
	}*/
	
	
	//main
	public static void main(String[] args) {
		


		System.out.println("Writing into Transaction Dump work sheet ......");
		boolean flag = false;
		String[] fileName = Constants.fileName;
		ConcurrentHashMap<Integer, List<TransactionDumpBean>> map = new ConcurrentHashMap<>();

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		Date date = new Date();
		String sysdate = formatter.format(date);
		//XSSFWorkbook workbook = null ;
		
		PreparedStatement ps = null;
		ResultSet rs2 = null;
		Connection con = null;
		long start=0;
		long end = 10000;
		try {		
			String folderPath = PropertyClass.getFilePath("");
			con = DBConnection.getConnection();
			int i = 0;
			//final XSSFSheet sheet = null;
			for (i = 0; i < fileName.length; i++) {
				int sheetCount = 0;
				 start=0;
				 end = 500000;
				
								
					System.out.println("Writing into " + folderPath
							+ fileName[i] + "_Transaction_Dump_Report_"
							+ sysdate + ".xlsx");
					
						

						
						//List<TransactionDumpBean> dataList = new ArrayList<TransactionDumpBean>();
						
					
					
						while(start<end) {
							final XSSFWorkbook workbook = new XSSFWorkbook();
						    /*final FileOutputStream out = new FileOutputStream(new File(folderPath
									+ fileName[i] + "_Transaction_Dump_Report_"
									+ sysdate+"_"+sheetCount + ".xlsx"));*/
							final XSSFSheet sheet = workbook.createSheet(fileName[i] + "_"
									+ sheetCount);
							//sheetCount++;
							String sql2 = "select CG_TRXN_ID,DATE_TIMESTAMP,MSISDN,SERVICE_ID,EVENT_ID,MERCHANT_ID,SUBSCRIPTION,CHANNEL_MODE,CONSENT_MODE,API1_RESPONSE_TIME,API2_RESPONSE_TIME," + 
									"ACTIVATION_STATUS" + 
									" from (" + 
									"select CG_TRXN_ID,DATE_TIMESTAMP,MSISDN,SERVICE_ID,EVENT_ID,MERCHANT_ID,SUBSCRIPTION,CHANNEL_MODE,CONSENT_MODE,API1_RESPONSE_TIME,API2_RESPONSE_TIME,ACTIVATION_STATUS," + 
									"row_number() OVER (order by CG_TRXN_ID asc) rn from TRANSACTION_DUMP where CG_FLOW='"+fileName[i]+"') a " + 
									"where rn between "+ start +" and "+ end;
							
							System.out.println(sql2);
							ps = con.prepareStatement(sql2);
							Random random = new Random();
							 Integer key1 = random.nextInt(1000000);
							 //System.out.println(dataList);
							  if(map.putIfAbsent(key1, new ArrayList<>()) == null) {
								  
								  Integer key = key1;
							  
							
							
							rs2 = ps.executeQuery();
							while (rs2.next()) {
								TransactionDumpBean bean = new TransactionDumpBean();

								bean.setCG_TRXN_ID(rs2.getString("CG_TRXN_ID"));
								//System.out.println(rs2.getString("CG_TRXN_ID"));
								bean.setDATE_TIMESTAMP(rs2
										.getTimestamp("DATE_TIMESTAMP"));
								bean.setMSISDN(rs2.getLong("MSISDN"));
								bean.setSERVICE_ID(rs2.getString("SERVICE_ID"));
								bean.setEVENT_ID(rs2.getString("EVENT_ID"));
								bean.setMERCHANT_ID(rs2.getString("MERCHANT_ID"));
								bean.setSUBSCRIPTION(rs2.getString("SUBSCRIPTION"));
								bean.setCHANNEL_MODE(rs2.getString("CHANNEL_MODE"));
								bean.setCONSENT_MODE(rs2.getString("CONSENT_MODE"));
								bean.setAPI1_RESPONSE_TIME(rs2
										.getInt("API1_RESPONSE_TIME"));
								bean.setAPI2_RESPONSE_TIME(rs2
										.getInt("API2_RESPONSE_TIME"));
								bean.setACTIVATION_STATUS(rs2
										.getString("ACTIVATION_STATUS"));

								//dataList.add(bean);
								map.get(key).add(bean);
								
							}
							if(map.get(key).size()>0) {
								final FileOutputStream out = new FileOutputStream(new File(folderPath
										+ fileName[i] + "_Transaction_Dump_Report_"
										+ sysdate+"_"+sheetCount + ".xlsx"));
								sheetCount++;
								start = end+1;
								end = end+500000;
								System.out.println("KEY : "+key);
								System.out.println("*****************************************************Data List size in main : "+map.get(key).size());
								Runnable t = new Runnable(){
									public void run(){
										 
										SchedulerMultiThreadUtility schedulerMultiThreadUtility = new SchedulerMultiThreadUtility(map,sheet,workbook,key,out);
										//schedulerMultiThreadUtility.setSheet(sheet);
										System.out.println("KEY : "+key);
										System.out.println("*****************************************************Data List size in main : "+map.get(key).size());
										
										new Thread(schedulerMultiThreadUtility).run();
										try {
											workbook.write(out);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
									}
								};
								new Thread(t).start();
								/*if(fetchAndWriteToXLSX(sheet,map,key,workbook,out)) {
									System.out.println("Creating Thread ........");
									
								}*/
							//	excelFileTransactionDump(out,sheet,workbook,dataList);
								
							}else {
								
								//start = 0;
								end = 0;
							}
								
							  }
							

						
						
						//System.out.println("File Generated : " + flag);
					
						
						
					
						}
						
						
						
			}
			
		
		} catch (Exception e) {
			
			
			e.printStackTrace();
		}finally {
			
			try {
				if (con != null)
					con.close();
				if (ps != null)
					ps.close();
				if (rs2 != null)
					rs2.close();
				

			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}

		}

		System.out.println("Completed !!!");
	
	

	}

}
