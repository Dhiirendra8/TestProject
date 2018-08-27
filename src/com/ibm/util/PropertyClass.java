package com.ibm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class PropertyClass {

	public static Properties getProperty() {		
		Properties prop = new Properties();
		InputStream is = null;
        try {
        	
        	is = new FileInputStream(Constants.prop_file);
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static String getFilePath(String folder) {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyy");
		Date date = new Date();
		String strDate = formatter.format(date);
		 //System.out.println(strDate);
		 String filePath = getProperty().getProperty("folder_Path")+strDate+folder;
		 File file = new File(filePath);
	        if (!file.exists()) {
	            if (file.mkdirs()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }else {
	        	System.out.println("Directory Exists");
	        }
	        
	        return filePath+"/";
	}
}
