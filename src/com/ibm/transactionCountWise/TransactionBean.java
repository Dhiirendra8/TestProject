package com.ibm.transactionCountWise;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.bean.TransactionDumpBean;
import com.ibm.report.TransactionDump;

public class TransactionBean extends Thread{
	private  XSSFWorkbook workbook ;
	private FileOutputStream out;
	private List<TransactionDumpBean> dataList ;
	private XSSFSheet sheet;
	public TransactionBean( XSSFWorkbook workbook,FileOutputStream out,List<TransactionDumpBean> dataList,XSSFSheet sheet) {
		this.workbook = workbook;
		this.out = out;
		this.sheet = sheet;
		this.dataList = dataList;
	}
	public void run() {
		System.out.println(Thread.currentThread().getName());
		
		System.out.println(dataList.size());
		synchronized (out) {
			TransactionDump.excelFileTransactionDump(out, sheet, workbook, dataList);
		}

	}

	
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	public FileOutputStream getOut() {
		return out;
	}
	public void setOut(FileOutputStream out) {
		this.out = out;
	}
	public  List<TransactionDumpBean> getDataList() {
		return dataList;
	}
	public void setDataList(List<TransactionDumpBean> dataList) {
		this.dataList = dataList;
	}
	public XSSFSheet getSheet() {
		return sheet;
	}
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}
}
