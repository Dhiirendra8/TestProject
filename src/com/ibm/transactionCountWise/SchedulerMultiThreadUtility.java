package com.ibm.transactionCountWise;


import java.io.FileOutputStream;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean.TransactionDumpBean;




public class SchedulerMultiThreadUtility implements Runnable{

	private ConcurrentMap<Integer, List<TransactionDumpBean>> map;
	private Connection conn = null;
	private XSSFSheet sheet;
	private XSSFWorkbook workbook;
	private List<TransactionDumpBean> dataList;
	private FileOutputStream out ;
	Integer key;
	
	public SchedulerMultiThreadUtility(ConcurrentMap<Integer, List<TransactionDumpBean>> map,XSSFSheet sheet,XSSFWorkbook workbook,Integer key,FileOutputStream out) {
		super();
		this.map = map;
		this.sheet = sheet;
		this.workbook = workbook;
		this.key = key;
	}
	/*public ConcurrentMap<Integer, List<TransactionDumpBean>> getMap() {
		return map;
	}
	public void setMap(ConcurrentMap<Integer, List<TransactionDumpBean>> map) {
		this.map = map;
	}
	public FileOutputStream getOut() {
		return out;
	}
	public void setOut(FileOutputStream out) {
		this.out = out;
	}
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public XSSFSheet getSheet() {
		return sheet;
	}
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
	public synchronized List<TransactionDumpBean> getDataList() {
		return dataList;
	}
	public synchronized void setDataList(List<TransactionDumpBean> dataList) {
		this.dataList = dataList;
	}*/
	
	@Override
	public void run() {

		String methodName = " SchedulerMultiThreadCircleWiseUtility Thread : ";
		ExecutorService executor = null;
		Future<String> future = null;
		try {
			System.out.println(methodName+"Started");
			System.out.println("getting merchant wise dump");
			executor = Executors.newSingleThreadExecutor();
			future = executor.submit(new Task());
			String threadTimeOut = "3";
			int timeOut = 3;
			if(null != threadTimeOut){
				timeOut = Integer.parseInt(threadTimeOut);
			}
			future.get(timeOut, TimeUnit.HOURS);
			System.out.println(methodName+"Finished");
		} catch (Exception e) {
			System.out.println(methodName+" Terminated : "+e.getMessage());
		}finally{
			if(null != executor){
				
				if(!future.isDone()){
				
					try {
						
						if(null != conn){
							conn.close();
							conn = null;
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					catch (Exception e) {
					
						e.printStackTrace();
					}
					future.cancel(true);
					System.out.println(methodName+" task is cancelled manually ");
				}
				executor.shutdownNow();
				System.out.println(methodName+" executer shut down end ");
			}
		}
	}
	
	class Task implements Callable<String> {
		@Override
		public String call() throws Exception {

			String methodName = " write in file :: ";
			System.out.println(methodName+"starts");
			
		
			
			try {
				 /*Random random = new Random();
				 Integer key = random.nextInt(1000000);*/
				 /*System.out.println(dataList);
				  if(map.putIfAbsent(key, dataList) == null) {
					  System.out.println("*****************************************************Data List size in task : "+dataList.size());*/
				boolean flag = excelFileTransactionDump(out, sheet, workbook,  map,key);
				 
				System.out.println("Flag : "+flag);
				//  }
			}catch (Exception e) {
				System.out.println(methodName+" : Exception found while write to file : "+e);
				e.printStackTrace();
			}
			finally{}
			System.out.println(methodName+" Ends");
			return "";
		}
	}
	
	
	public static  boolean excelFileTransactionDump(FileOutputStream out,
			XSSFSheet sheet, XSSFWorkbook workbook,
			ConcurrentMap<Integer, List<TransactionDumpBean>> map, Integer key) {
		System.out.println("******Inside excelFileTransactionDump method*****");
		
			  
		  
		if(map.get(key)!=null)
		System.out.println("Data List size : " + map.get(key).size());
		boolean flag = true;
		List<String> list = new ArrayList<String>();
		list.add("CG Trxn Id");
		list.add("Date & Time Stamp");
		list.add("MSISDN");
		list.add("Service Id");
		list.add("Event Id");
		list.add("Merchant Id");
		list.add("Subscription/ PPU");
		list.add("Channel Mode");
		list.add("Consent Mode");
		list.add("API1 Response");
		list.add("API2 Response");
		list.add("Activation Status");

		XSSFCell cell;
		XSSFRow row1 = sheet.createRow(1);

		XSSFCellStyle style0 = workbook.createCellStyle();
		XSSFColor color0 = new XSSFColor(new java.awt.Color(255, 217, 102));
		style0.setFillForegroundColor(color0);
		style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style0.setBorderLeft(BorderStyle.THIN);
		style0.setBorderRight(BorderStyle.THIN);
		style0.setBorderTop(BorderStyle.THIN);
		style0.setBorderBottom(BorderStyle.THIN);
		style0.setWrapText(true);
		style0.setAlignment(HorizontalAlignment.CENTER);

		DataFormat format = workbook.createDataFormat();
		XSSFCellStyle style1 = workbook.createCellStyle();
		style1.setWrapText(true);
		style1.setAlignment(HorizontalAlignment.CENTER);
		style1.setDataFormat(format.getFormat("#"));

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setWrapText(true);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setDataFormat(format.getFormat("dd-MM-yy  h:mm:ss"));

		// style0.setFont(font0);

		int i = 0, j;
		for (String col : list) {
			cell = row1.createCell(i++);
			cell.setCellValue(col);
			cell.setCellStyle(style0);
			sheet.setColumnWidth(cell.getColumnIndex(), 2500);

		}
		System.out.println("After list for loop");
		XSSFRow row;
		i = 2;
		
		if(map.get(key) != null) {
			//for (TransactionDumpBean bean : map.get(key)) {
			for(int k=0;k<map.get(key).size();k++) {
				j = 0;
				row = sheet.createRow(i);

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getCG_TRXN_ID());
				cell.setCellValue(map.get(key).get(k).getCG_TRXN_ID());
				

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getDATE_TIMESTAMP());
				cell.setCellValue(map.get(key).get(k).getDATE_TIMESTAMP());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getMSISDN());
				cell.setCellValue(map.get(key).get(k).getMSISDN());

				cell = row.createCell(j++);
			//	cell.setCellValue(bean.getSERVICE_ID());
				cell.setCellValue(map.get(key).get(k).getSERVICE_ID());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getEVENT_ID());
				cell.setCellValue(map.get(key).get(k).getEVENT_ID());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getMERCHANT_ID());
				cell.setCellValue(map.get(key).get(k).getMERCHANT_ID());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getSUBSCRIPTION());
				cell.setCellValue(map.get(key).get(k).getSUBSCRIPTION());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getCHANNEL_MODE());
				cell.setCellValue(map.get(key).get(k).getCHANNEL_MODE());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getCONSENT_MODE());
				cell.setCellValue(map.get(key).get(k).getCONSENT_MODE());
				
				cell = row.createCell(j++);
				//cell.setCellValue(bean.getAPI1_RESPONSE_TIME());
				cell.setCellValue(map.get(key).get(k).getAPI1_RESPONSE_TIME());

				cell = row.createCell(j++);
				//cell.setCellValue(bean.getAPI2_RESPONSE_TIME());
				cell.setCellValue(map.get(key).get(k).getAPI2_RESPONSE_TIME());
				
				
				cell = row.createCell(j++);
				//cell.setCellValue(bean.getACTIVATION_STATUS());
				cell.setCellValue(map.get(key).get(k).getACTIVATION_STATUS());

				i++;
				
			}
			
			/*System.out.println("In If part");
			j = 0;
			row = sheet.createRow(i);

			cell = row.createCell(j);
			cell.setCellValue("data found");
			sheet.addMergedRegion(new CellRangeAddress(i, i, j, list.size()));*/
		}else {
			System.out.println("In else part");
			j = 0;
			row = sheet.createRow(i);

			cell = row.createCell(j);
			cell.setCellValue("No data found");
			sheet.addMergedRegion(new CellRangeAddress(i, i, j, list.size()));
			
		}
		
	
		System.out.println("******Exit excelFileTransactionDump method*****");
		return flag;

	}

	
}
