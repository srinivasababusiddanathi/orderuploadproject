package com.progresssoft.fileupload.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.progresssoft.fileupload.dao.JpaDAO;
import com.progresssoft.fileupload.dao.Order;
import com.progresssoft.fileupload.model.UploadFile;

/**
 * ServiceImpl class to upload file process
 * 
 * @version 1.0
 * @author Srinivas
 */

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private JpaDAO jpaDao;

	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

	/**
	 * Reads uploaded file content
	 * 
	 * @param file uploadFile
	 * @return list of orders
	 */

	@Override
	public List<Order> readDataFromFile(UploadFile file) {
		logger.info(" **** Service Start : readDataFromFile() ****");
		List<Order> orders = new ArrayList<>();
		MultipartFile multipartFile = file.getFile();
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			orders = bufferedReader.lines().parallel().map(mapToOrder).collect(Collectors.toList());
			bufferedReader.close();
		} catch (IOException e) {
			logger.info("Error while reading file " + e.getMessage());
		}
		return orders;
	}

	private Function<String, Order> mapToOrder = (line) -> {
		String[] vales = line.split(",");
		if (vales.length < 5 || vales.length > 5) {
			return new Order(null, null, null, null, 0, Arrays.deepToString(vales));
		}
		return new Order(vales[0], vales[1], vales[2], vales[3], Float.parseFloat(vales[4]), null);
	};

	/**
	 * Split uploaded list
	 * 
	 * @param orderList List<Order>
	 * @param targetSize int
	 * @param fileName String file name
	 * 
	 * @return void
	 */

	@Override
	public void writeDataToDB(List<Order> orderList, int targetSize, String fileName) {
		JpaDAO dao = new JpaDAO(fileName, targetSize);
		List<List<Order>> output = ListUtils.partition(orderList, targetSize);
		output.stream().parallel().forEach(order -> {
			try {
				dao.createOrder(order,fileName);
			} catch (Exception e) {
				logger.info(" Error while saving data into database " + e.getMessage());
				throw e;
			}
		});
	}

	@Override
	public Integer saveUploadFileDetails(String fileName) {
		return jpaDao.saveFileDetails(fileName);
	}

	@Override
	public int checkDuplicateFile(String fileName) {
		return jpaDao.checkDuplicate(fileName);
	}

	@Override
	public void saveCurrencyCountDetails(String fileName) {
		jpaDao.saveCurrencyCountDetails(fileName);

	}

}
