package com.ibm.bean;


import java.sql.Timestamp;
import java.sql.Date;



public class TransactionDumpBean {
	
	private String CG_TRXN_ID;
	private Timestamp DATE_TIMESTAMP;
	private long MSISDN;
	private String SERVICE_ID;
	private String EVENT_ID;
	private String MERCHANT_ID;
	private String SUBSCRIPTION;
	private String CHANNEL_MODE;
	private String CONSENT_MODE;
	private int API1_RESPONSE_TIME;
	private int API2_RESPONSE_TIME;
	private String ACTIVATION_STATUS;
	private Date UPLOAD_DATE;
	
	public String getCG_TRXN_ID() {
		return CG_TRXN_ID;
	}
	public void setCG_TRXN_ID(String cG_TRXN_ID) {
		CG_TRXN_ID = cG_TRXN_ID;
	}
	public Timestamp getDATE_TIMESTAMP() {
		return DATE_TIMESTAMP;
	}
	public void setDATE_TIMESTAMP(Timestamp timestamp) {
		DATE_TIMESTAMP = timestamp;
	}
	public long getMSISDN() {
		return MSISDN;
	}
	public void setMSISDN(long mSISDN) {
		MSISDN = mSISDN;
	}
	public String getSERVICE_ID() {
		return SERVICE_ID;
	}
	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}
	public String getEVENT_ID() {
		return EVENT_ID;
	}
	public void setEVENT_ID(String eVENT_ID) {
		EVENT_ID = eVENT_ID;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANT_ID) {
		MERCHANT_ID = mERCHANT_ID;
	}
	public String getSUBSCRIPTION() {
		return SUBSCRIPTION;
	}
	public void setSUBSCRIPTION(String sUBSCRIPTION) {
		SUBSCRIPTION = sUBSCRIPTION;
	}
	public String getCHANNEL_MODE() {
		return CHANNEL_MODE;
	}
	public void setCHANNEL_MODE(String cHANNEL_MODE) {
		CHANNEL_MODE = cHANNEL_MODE;
	}
	public String getCONSENT_MODE() {
		return CONSENT_MODE;
	}
	public void setCONSENT_MODE(String cONSENT_MODE) {
		CONSENT_MODE = cONSENT_MODE;
	}
	public int getAPI1_RESPONSE_TIME() {
		return API1_RESPONSE_TIME;
	}
	public void setAPI1_RESPONSE_TIME(int aPI1_RESPONSE_TIME) {
		API1_RESPONSE_TIME = aPI1_RESPONSE_TIME;
	}
	public int getAPI2_RESPONSE_TIME() {
		return API2_RESPONSE_TIME;
	}
	public void setAPI2_RESPONSE_TIME(int aPI2_RESPONSE_TIME) {
		API2_RESPONSE_TIME = aPI2_RESPONSE_TIME;
	}
	public String getACTIVATION_STATUS() {
		return ACTIVATION_STATUS;
	}
	public void setACTIVATION_STATUS(String aCTIVATION_STATUS) {
		ACTIVATION_STATUS = aCTIVATION_STATUS;
	}
	public Date getUPLOAD_DATE() {
		return UPLOAD_DATE;
	}
	public void setUPLOAD_DATE(Date uPLOAD_DATE) {
		UPLOAD_DATE = uPLOAD_DATE;
	}
	
	
}
