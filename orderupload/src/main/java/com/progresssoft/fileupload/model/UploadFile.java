package com.progresssoft.fileupload.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * File Upload Model.
 * 
 * @version 1.0
 * @author Srinivas
 */

public class UploadFile {

	MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
