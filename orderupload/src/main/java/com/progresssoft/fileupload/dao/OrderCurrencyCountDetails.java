package com.progresssoft.fileupload.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * Entity Class for ORDER_CURRENCY_COUNT_DETAILS Table.
 * 
 * @version 1.0
 * @author Srinivas
 */

@Entity
@Table(name = "ORDER_CURRENCY_COUNT_DETAILS")
@NamedNativeQueries({ @NamedNativeQuery(name = "updateDealCount", query = "delete from ORDER_CURRENCY_COUNT_DETAILS", resultClass = OrderCurrencyCountDetails.class) })

public class OrderCurrencyCountDetails {

	@Id
	@Column(name = "ORDERING_CURRENCY")
	private String currencyISOCode;

	@Column(name = "DEAL_COUNT")
	private Float dealCount;
	
	@Column(name = "FILE_NAME")
	private String fileName;

	public String getCurrencyISOCode() {
		return currencyISOCode;
	}

	public void setCurrencyISOCode(String currencyISOCode) {
		this.currencyISOCode = currencyISOCode;
	}

	public Float getDealCount() {
		return dealCount;
	}

	public void setDealCount(Float dealCount) {
		this.dealCount = dealCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

			
}