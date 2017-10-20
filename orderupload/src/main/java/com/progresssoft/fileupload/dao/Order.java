package com.progresssoft.fileupload.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@NamedNativeQueries({ @NamedNativeQuery(name = "getDealCountByToCurrency", query = "select ID,ORDERING_CURRENCY,TO_CURRENCY,TIMESTAMP,count(ORDERING_CURRENCY) AS DEAL_AMOUNT,"
		+ " FILE_NAME FROM ORDER_DETAILS group by ORDERING_CURRENCY ", resultClass = Order.class) })

public class Order {

	@Id
	@Column(name = "ID")
	private int id;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	
	public Order(int id, String orderingCurrency, String toCurrency, String timeStamp, float dealAmount, String errorRecord) {
		super();
		this.id = id;
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

	/*@Override
	public String toString() {
		return "Order [id=" + id + ", orderingCurrency=" + orderingCurrency + ", toCurrency=" + toCurrency + ", timeStamp=" + timeStamp
				+ ", dealAmount=" + dealAmount + ", errorRecord=" + errorRecord + "]";
	}*/

}