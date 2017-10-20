package com.progresssoft.fileupload.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

/**
 * Entity Class for FILE_DETAILS Table.
 * 
 * @version 1.0
 * @author Srinivas
 */

@Entity
@Table(name = "FILE_DETAILS")
@NamedNativeQueries({ @NamedNativeQuery(name = "getAllFileDetailsByFileName", query = "SELECT FILE_ID,FILE_NAME "
		+ " FROM FILE_DETAILS WHERE FILE_DETAILS.FILE_NAME = ? ", resultClass = FileDetails.class) })

public class FileDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
	private int fileId;

	@Column(name = "FILE_NAME")
	private String fileName;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
