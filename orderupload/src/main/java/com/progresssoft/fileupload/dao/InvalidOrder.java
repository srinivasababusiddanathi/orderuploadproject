package com.progresssoft.fileupload.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity Class for INVALID_ORDERS Table.
 * 
 * @version 1.0
 * @author Srinivas
 */

@Entity
@Table(name = "INVALID_ORDERS")
public class InvalidOrder {

	@Id
	@Column(name = "INVALID_RECORD")
	private String invalidRecord;

	@Column(name = "FILE_NAME")
	private String fileName;

	public String getInvalidRecord() {
		return invalidRecord;
	}

	public void setInvalidRecord(String invalidRecord) {
		this.invalidRecord = invalidRecord;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
