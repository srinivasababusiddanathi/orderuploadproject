package com.progresssoft.fileupload.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.progresssoft.fileupload.constants.AppConstants;
import com.progresssoft.fileupload.model.UploadFile;

/**
 * Validator class for Upload file
 * 
 * @version 1.0
 * @author Srinivas
 */

public class FileValidator implements Validator {
	public boolean supports(Class<?> paramClass) {
		return UploadFile.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		UploadFile file = (UploadFile) obj;
		if (file.getFile().getSize() == 0) {
			errors.rejectValue("file", "valid.file");
		}
		if (file.getFile().getSize() > 0) {
			String fileName = file.getFile().getOriginalFilename();

			if (fileName.lastIndexOf(AppConstants.DOT) != -1 && fileName.lastIndexOf(AppConstants.DOT) != 0) {
				String fileNameExtn = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (!AppConstants.FILE_EXTN.equalsIgnoreCase(fileNameExtn.toLowerCase())) {
					errors.rejectValue("file", "valid.file.format");
				}

			}

		}
	}
}