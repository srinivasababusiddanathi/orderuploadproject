package com.progresssoft.fileupload.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DAO Class to insert data into Tables.
 * 
 * @version 1.0
 * @author Srinivas
 */

public class JpaDAO {

	private static final Logger logger = Logger.getLogger(JpaDAO.class.getName());
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("OrderUnit");
	String fileName = "";
	int batchSize = 10000;

	public JpaDAO() {
	}

	public JpaDAO(String fileName, int batchSize) {
		this.fileName = fileName;
		this.batchSize = batchSize;
	}

	/**
	 * Insert order details into database
	 * 
	 * @param orders List of orders
	 * @return void
	 */
	
	public void createOrder(List<Order> orders,String fileName) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		int i = 0;
		for (Order o : orders) {
			i++;

			if (o.getErrorRecord() != null) {
				InvalidOrder invalidOrder = new InvalidOrder();
				invalidOrder.setFileName(fileName);
				invalidOrder.setInvalidRecord(o.getErrorRecord());
				em.persist(invalidOrder);
			} else {
				Order order = (Order) o;
				order.setFileName(fileName);
				em.persist(order);
			}

			if (i > 0 && i % batchSize == 0) {
				em.flush();
				em.clear();
				try {
					em.getTransaction().commit();
				} catch (Exception e) {
					em.getTransaction().getRollbackOnly();
					logger.info("Error while saving order details " + e.getMessage());
				}
				em.getTransaction().begin();
			}
		}
		try {
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().getRollbackOnly();
		}
		em.close();
	}

	
	/**
	 * Save uploaded file name into database
	 * 
	 * @param fileName FileName
	 * @return file id
	 */

	
	public Integer saveFileDetails(String fileName) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		FileDetails fileDetails = new FileDetails();
		fileDetails.setFileName(fileName);
		try {
			em.persist(fileDetails);
			em.getTransaction().commit();
			return fileDetails.getFileId();
		} catch (Exception e) {
			logger.info(" Error while saving file details " + e.getMessage());
		}

		return null;

	}

	
	/**
	 * Check for duplicate file
	 * 
	 * @param fileName FileName
	 * @return file count
	 */

	
	public int checkDuplicate(String fileName) {
		EntityManager em = emf.createEntityManager();
		List<FileDetails> employees = em.createNamedQuery("getAllFileDetailsByFileName", FileDetails.class).setParameter(1, fileName).getResultList();
		return employees.size();
	}
	
	
	/**
	 *  Insert accumulative count of deals per Ordering Currency into database
	 * 
	 * @param fileName FileName
	 * @return void
	 */

	
	public void saveCurrencyCountDetails(String fileName) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		//Update deal count for all existing files
		em.createNamedQuery("updateDealCount", OrderCurrencyCountDetails.class).executeUpdate();
		List<Order> orderCurrencyList = em.createNamedQuery("getDealCountByToCurrency", Order.class).getResultList();
		for (Order currCount : orderCurrencyList) {
				OrderCurrencyCountDetails orderCurrencyCount = new OrderCurrencyCountDetails();
				orderCurrencyCount.setCurrencyISOCode(currCount.getOrderingCurrency());
				orderCurrencyCount.setDealCount(currCount.getDealAmount());
				orderCurrencyCount.setFileName(fileName);
				em.persist(orderCurrencyCount);
				
		     }
			try {
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().getRollbackOnly();
			}
			em.close();
			
			
   }
	
		}
