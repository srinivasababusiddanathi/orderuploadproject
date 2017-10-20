package com.progresssoft.fileupload.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.progresssoft.fileupload.constants.AppConstants;
import com.progresssoft.fileupload.dao.Order;
import com.progresssoft.fileupload.model.UploadFile;
import com.progresssoft.fileupload.service.OrderService;
import com.progresssoft.fileupload.validator.FileValidator;

/**
 * File Controller class helps to process file upload upload request and outputs
 * 
 * @version 1.0
 * @author Srinivas
 */

@Controller
@RequestMapping("/file.htm")
@Configuration
@PropertySource("classpath:validation.properties")
public class FileController {

	private static final Logger logger = Logger.getLogger(FileController.class.getName());

	@Autowired
	private OrderService orderService;

	@Autowired
	private FileValidator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	/**
	 * load file upload page
	 * 
	 * @param model Model
	 * @param file UploadFile
	 * @return result view.
	 */
	
	@RequestMapping(method = RequestMethod.GET)
	public String getForm(Model model, ModelMap modelMap) {
		UploadFile uploadFile = new UploadFile();
		model.addAttribute("uploadFile", uploadFile);
		return AppConstants.UPLOAD_PAGE;
	}

	/**
	 * Reads uploaded file content
	 * 
	 * @param model Model
	 * @param file UploadFile
	 * @param result BindingResult
	 * @param modelMap ModelMap
	 * 
	 * @return result view.
	 */

	@RequestMapping(method = RequestMethod.POST)
	public String fileUploaded(Model model, @Validated UploadFile file, BindingResult result, ModelMap modelMap) {
		logger.info("**** Start : Uploading File ****");
		long startTime = getCurrentTime();
		int targetSize = 10000;

		//Throw errors in case of any invalid file format or duplicate file format
		if (!result.hasErrors()) {
			String fileName = file.getFile().getOriginalFilename();
			int fileCount = orderService.checkDuplicateFile(fileName);
			if(fileCount > 0) {
				modelMap.addAttribute("errorMsg", "\'"+fileName+"\' was uploaded already. Please upload a different file.");
				return AppConstants.UPLOAD_PAGE;
			}
	
			
			// Read data and construct orders list
			List<Order> orders = orderService.readDataFromFile(file);
			if(!orders.isEmpty()) {
				// Save uploaded file name only into database
				orderService.saveUploadFileDetails(fileName);
	
				try {
					orderService.writeDataToDB(orders, targetSize, file.getFile().getOriginalFilename());
				} catch (Exception e) {
					modelMap.addAttribute("errorMsg", "Error in while processing file");
					return AppConstants.UPLOAD_PAGE;
				}
	
				long endTime = getCurrentTime();
				float seconds = Long.valueOf(endTime - startTime).floatValue() / 1000;
				
				
				// Calculate accumulative count of deals per Ordering Currency into database
				 orderService.saveCurrencyCountDetails(fileName);
				modelMap.addAttribute("successMsg", "<b>File uploaded sucessfully...</b> Time taken to execute <b>"+orders.size()+"</b> records is <b>" + seconds+" (sec)</b>");
			}else {
				modelMap.addAttribute("errorMsg", "No Orders found in the uploaded file.");
			}
		}
		logger.info("**** Uploading File : End *********");
		return AppConstants.UPLOAD_PAGE;
	}

	public long getCurrentTime() {
		return new Timestamp(new Date().getTime()).getTime();
	}

}
