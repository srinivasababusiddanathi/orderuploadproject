package com.progresssoft.fileupload.service;

import java.util.List;

import com.progresssoft.fileupload.dao.Order;

/**
 * Service class to upload file process
 * 
 * @version 1.0
 * @author Srinivas
 */

import com.progresssoft.fileupload.model.UploadFile;

public interface OrderService {

	List<Order> readDataFromFile(UploadFile file);

	void writeDataToDB(List<Order> orderList, int targetSize, String csvFileName);

	Integer saveUploadFileDetails(String fileName);

	int checkDuplicateFile(String fileName);
	
	void saveCurrencyCountDetails(String fileName);
	

}
