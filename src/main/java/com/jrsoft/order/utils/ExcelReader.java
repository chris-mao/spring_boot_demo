/**
 * 
 */
package com.jrsoft.order.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.DataFormatException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.jrsoft.order.entity.OrderHeader;
import com.jrsoft.order.entity.OrderLine;

/**
 * read and parse the xls file
 * 
 * @author chrismao
 *
 */
public class ExcelReader implements DataReader {

	/**
	 * the sheet name within the template
	 */
	private final static String _SHEET_NAME = "Batch-Import";

	/**
	 * start row index
	 */
	private final static int _START_ROW_INDEX = 3;

	/**
	 * column index
	 */
	private final static int _CUSTOMER_PO_INDEX = 0;
	private final static int _CUSTOMER_ACCOUNT_INDEX = 1;
	private final static int _OPERATING_UNIT_INDEX = 2;
	private final static int _CONTACT_INDEX = 3;
	private final static int _CREATOR_INDEX = 4;
	private final static int _ORDER_TYPE_INDEX = 5;
	private final static int _CURRENCY_INDEX = 6;
	private final static int _DELIVER_TO_INDEX = 7;
	private final static int _INVENTORY_ITEM_INDEX = 8;
	private final static int _CUSTOMER_ITEM_INDEX = 9;
	private final static int _QTY_INDEX = 10;
	private final static int _REQUEST_DATE_INDEX = 11;
	private final static int _PRICE_INDEX = 12;

	/**
	 * read the template and parser the data
	 * 
	 * @param inputFileName
	 *            the file name, should include full path
	 * 
	 * @return ArrayList
	 * 
	 * @throws IOException
	 *             if the file dose not exists
	 * @throws DataFormatException
	 *             if the template's format cannot accept by program
	 */
	public ArrayList<OrderHeader> readFile(String inputFileName) throws IOException, DataFormatException {
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		FileInputStream fis = null;
		OrderHeader order = new OrderHeader();
		ArrayList<OrderHeader> result = new ArrayList<OrderHeader>();

		File file = new File(inputFileName);
		if (false == file.exists()) {
			throw new FileNotFoundException("The file which you given does not exists");
		}

		System.out.print("Loading the file......");

		try {
			fis = new FileInputStream(inputFileName);
			wb = new HSSFWorkbook(fis);
			sheet = wb.getSheet(ExcelReader._SHEET_NAME);
			if (null == sheet) {
				throw new DataFormatException(
						"the template file should have one sheet at least, and named 'Batch-Import'!!");
			}
			System.out.println("okay!");
			System.out.print("Reading the file......");
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = (HSSFRow) rowIterator.next();
				if (row.getRowNum() < ExcelReader._START_ROW_INDEX) {
					continue;
				}
				order = parserRow(order, row);
				if ((null != order) && (!result.contains(order))) {
					result.add(order);
				}
			}
			System.out.println("okay!");
			return result;
		} finally {
			fis.close();
			wb.close();
			// System.out.println("The file was closed.");
			System.out.println();
		}
	}

	private String getStringValue(Cell cell) {
		if (null == cell)
			return "";
		try {
			return cell.getStringCellValue();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			return sb.append(cell.getNumericCellValue()).toString();
		}
	}

	/**
	 * 数据转换 如果客户采购单号为空，则表示为订单行，不读取订单头信息 如果工厂型号和客户型号同时为空，则表示不读取该行
	 * 
	 * @param order
	 * @param row
	 * @return
	 */
	private OrderHeader parserRow(OrderHeader order, Row row) {
		String accountNumber = "";
		String operatingUnit = "";
		String contact = "";
		String creator = "";
		String orderType = "";
		String currency = "";
		String deliverTo = "";
		Cell cell;
		cell = row.getCell(ExcelReader._CUSTOMER_PO_INDEX);
		String customerPO = getStringValue(cell);

		if (!customerPO.isEmpty()) {
			cell = row.getCell(ExcelReader._CUSTOMER_ACCOUNT_INDEX);
			accountNumber = getStringValue(cell);
			cell = row.getCell(ExcelReader._OPERATING_UNIT_INDEX);
			operatingUnit = getStringValue(cell);
			cell = row.getCell(ExcelReader._CONTACT_INDEX);
			contact = getStringValue(cell);
			cell = row.getCell(ExcelReader._CREATOR_INDEX);
			creator = getStringValue(cell);
			cell = row.getCell(ExcelReader._ORDER_TYPE_INDEX);
			orderType = getStringValue(cell);
			cell = row.getCell(ExcelReader._CURRENCY_INDEX);
			currency = getStringValue(cell);
			cell = row.getCell(ExcelReader._DELIVER_TO_INDEX);
			deliverTo = getStringValue(cell);
		}
		cell = row.getCell(ExcelReader._INVENTORY_ITEM_INDEX);
		String inventoryItem = getStringValue(cell);
		cell = row.getCell(ExcelReader._CUSTOMER_ITEM_INDEX);
		String customerItem = getStringValue(cell);
		cell = row.getCell(ExcelReader._QTY_INDEX);
		int qty = (int) ((null == cell) ? 0 : cell.getNumericCellValue());
		cell = row.getCell(ExcelReader._REQUEST_DATE_INDEX);
		Date requestDate = (null == cell) ? new Date() : cell.getDateCellValue();
		cell = row.getCell(ExcelReader._PRICE_INDEX);
		double sellingPrice = (double) ((null == cell) ? 0 : cell.getNumericCellValue());
		if (inventoryItem.isEmpty() && customerItem.isEmpty())
			return order;

		OrderHeader result;
		if (!customerPO.isEmpty()) {
			result = new OrderHeader();
			result.setCustomerPO(customerPO);
			result.setAccountNumber(accountNumber);
			result.setOperatingUnit(operatingUnit);
			result.setContact(contact);
			result.setCreator(creator);
			result.setOrderType(orderType);
			result.setCurrency(currency);
			result.setDeliverTo(deliverTo);

		} else {
			result = order;
		}

		OrderLine line = new OrderLine();
		// if (!inventoryItem.isEmpty()) {
		// line.setInventoryModel();
		// } else if (!customerItem.isEmpty()) {
		// line.setCustomerItem(customerItem);
		// }
		line.setQty(qty);
		line.setRequestDate(requestDate);
		line.setSellingPrice(sellingPrice);
		result.addOrderLine(line);

		return result;
	}
}
