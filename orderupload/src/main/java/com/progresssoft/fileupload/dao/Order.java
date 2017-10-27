package com.progresssoft.fileupload.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity Class for Orders Table.
 * 
 * @version 1.0
 * @author Srinivas
 */

@Entity
@Table(name = "ORDER_DETAILS")
@NamedNativeQueries({ @NamedNativeQuery(name = "getDealCountByToCurrency", query = "select ORDER_ID,DEAL_ID,ORDERING_CURRENCY,TO_CURRENCY,TIMESTAMP,count(ORDERING_CURRENCY) AS DEAL_AMOUNT,"
		+ " FILE_NAME FROM ORDER_DETAILS group by ORDERING_CURRENCY ", resultClass = Order.class) })

public class Order {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private int orderId;

	@Column(name = "DEAL_ID")
	private String dealId;

	@Column(name = "ORDERING_CURRENCY")
	private String orderingCurrency;

	@Column(name = "TO_CURRENCY")
	private String toCurrency;

	@Column(name = "TIMESTAMP")
	private String timeStamp;

	@Column(name = "DEAL_AMOUNT")
	private Float dealAmount;
	
	@Column(name = "FILE_NAME")
	private String fileName;


	@Transient
	private String errorRecord;

	public Order() {

	}


	public String getOrderingCurrency() {
		return orderingCurrency;
	}

	public void setOrderingCurrency(String orderingCurrency) {
		this.orderingCurrency = formatString(orderingCurrency);
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = formatString(toCurrency);
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = formatString(timeStamp);
	}

	
	public Order(String dealId, String orderingCurrency, String toCurrency, String timeStamp, float dealAmount, String errorRecord) {
		super();
		this.dealId = dealId;
		this.orderingCurrency = formatString(orderingCurrency);
		this.toCurrency = formatString(toCurrency);
		this.timeStamp = formatString(timeStamp);
		this.dealAmount = dealAmount;
		this.errorRecord = errorRecord;

	}

	public String getErrorRecord() {
		return errorRecord;
	}

	public void setErrorRecord(String errorRecord) {
		this.errorRecord = errorRecord;
	}

	private String formatString(String inputStr) {
		if (inputStr != null && !inputStr.trim().equals(""))
			return inputStr.replace("\"", "").replaceAll("\'", "").trim();
		return inputStr;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Float getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(Float dealAmount) {
		this.dealAmount = dealAmount;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getDealId() {
		return dealId;
	}


	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/*@Override
	public String toString() {
		return "Order [id=" + id + ", orderingCurrency=" + orderingCurrency + ", toCurrency=" + toCurrency + ", timeStamp=" + timeStamp
				+ ", dealAmount=" + dealAmount + ", errorRecord=" + errorRecord + "]";
	}*/

}