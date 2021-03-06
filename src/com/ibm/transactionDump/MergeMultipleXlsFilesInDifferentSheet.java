package com.ibm.transactionDump;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.util.PropertyClass;

public class MergeMultipleXlsFilesInDifferentSheet {
	
	 public static void mergeExcelFiles(File file) throws IOException {
		    XSSFWorkbook book = new XSSFWorkbook();
		    System.out.println(file.getName());
		    String directoryName = PropertyClass.getFilePath("/Transaction_Dump/");
		    File directory = new File(directoryName);
		    //get all the files from a directory
		    File[] fList = directory.listFiles();
		    for (File file1 : fList){
		        if (file1.isFile()){
		            String ParticularFile = file1.getName();
		       FileInputStream fin = new FileInputStream(new File(directoryName+"\\"+ParticularFile));
		      XSSFWorkbook b = new XSSFWorkbook(fin);
		      for (int i = 0; i < b.getNumberOfSheets(); i++) {
		          XSSFSheet sheet = book.createSheet(b.getSheetName(i));
		        copySheets(book, sheet, b.getSheetAt(i));
		        System.out.println(b.getSheetName(i) +" sheet is  Copying ...");
		      }
		    }
		    try {
		      writeFile(book, file);
		    }catch(Exception e) {
		        e.printStackTrace();
		    }
		   }
		  }
		  protected static void writeFile(XSSFWorkbook book, File file) throws Exception {
		    FileOutputStream out = new FileOutputStream(file);
		    book.write(out);
		    out.close();
		  }
		  private static void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet){     
		    copySheets(newWorkbook, newSheet, sheet, true);
		  }     

		  private static void copySheets(XSSFWorkbook newWorkbook, XSSFSheet newSheet, XSSFSheet sheet, boolean copyStyle){     
		    int newRownumber = newSheet.getLastRowNum();
		    int maxColumnNum = 0;     
		    Map<Integer, XSSFCellStyle> styleMap = (copyStyle) ? new HashMap<Integer, XSSFCellStyle>() : null;    

		    for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {     
		      XSSFRow srcRow = sheet.getRow(i);     
		      XSSFRow destRow = newSheet.createRow(i + newRownumber);     
		      if (srcRow != null) {     
		        copyRow(newWorkbook, sheet, newSheet, srcRow, destRow, styleMap);     
		        if (srcRow.getLastCellNum() > maxColumnNum) {     
		            maxColumnNum = srcRow.getLastCellNum();     
		        }     
		      }     
		    }     
		    for (int i = 0; i <= maxColumnNum; i++) {     
		      newSheet.setColumnWidth(i, sheet.getColumnWidth(i));     
		    }     
		  }     

		  public static void copyRow(XSSFWorkbook newWorkbook, XSSFSheet srcSheet, XSSFSheet destSheet, XSSFRow srcRow, XSSFRow destRow, Map<Integer, XSSFCellStyle> styleMap) {     
		    destRow.setHeight(srcRow.getHeight());
		    for (int j = srcRow.getFirstCellNum(); j <= srcRow.getLastCellNum(); j++) {     
		      XSSFCell oldCell = srcRow.getCell(j);
		      XSSFCell newCell = destRow.getCell(j);
		      if (oldCell != null) {     
		        if (newCell == null) {     
		          newCell = destRow.createCell(j);     
		        }     
		        copyCell(newWorkbook, oldCell, newCell, styleMap);
		      }     
		    }                
		  }

		  public static void copyCell(XSSFWorkbook newWorkbook, XSSFCell oldCell, XSSFCell newCell, Map<Integer, XSSFCellStyle> styleMap) {      
		    if(styleMap != null) {     
		      int stHashCode = oldCell.getCellStyle().hashCode();     
		      XSSFCellStyle newCellStyle = styleMap.get(stHashCode);     
		      if(newCellStyle == null){     
		        newCellStyle = newWorkbook.createCellStyle();     
		        newCellStyle.cloneStyleFrom(oldCell.getCellStyle());     
		        styleMap.put(stHashCode, newCellStyle);     
		      }     
		      newCell.setCellStyle(newCellStyle);   
		    }     
		    switch(oldCell.getCellType()) {     
		      case XSSFCell.CELL_TYPE_STRING:     
		        newCell.setCellValue(oldCell.getRichStringCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_NUMERIC:     
		        newCell.setCellValue(oldCell.getNumericCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_BLANK:     
		        newCell.setCellType(XSSFCell.CELL_TYPE_BLANK);     
		        break;     
		      case XSSFCell.CELL_TYPE_BOOLEAN:     
		        newCell.setCellValue(oldCell.getBooleanCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_ERROR:     
		        newCell.setCellErrorValue(oldCell.getErrorCellValue());     
		        break;     
		      case XSSFCell.CELL_TYPE_FORMULA:     
		        newCell.setCellFormula(oldCell.getCellFormula());     
		        break;     
		      default:     
		        break;     
		    }
		  }
		  public static void main(String[] args) {
		      try {
		        mergeExcelFiles(new File(PropertyClass.getFilePath("")+"/XlsfileWhereDataWillBeMerged.xlsx"));
		        
		        FileUtils.deleteDirectory(new File( PropertyClass.getFilePath("/Transaction_Dump/")));
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		      
		      System.out.println("Completed !!!");
		}
}
