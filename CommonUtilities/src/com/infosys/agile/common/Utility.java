package com.infosys.agile.common;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public abstract class Utility
{

	/**
	 * To send java email notifications
	 * 
	 * @param fromEmailAddresss
	 * @param toEmailAddress
	 * @param ccEmailAddress
	 * @param emailSubject
	 * @param emailContent
	 * @return boolean
	 * @throws AddressException
	 * @throws MessagingException
	 */

	public static boolean sendMailNotification(final String fromEmailAddresss, final String toEmailAddress,
			final String ccEmailAddress, final String emailSubject, final String emailContent, final String hostName)
					throws AddressException, MessagingException {

		final Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", hostName);
		final Session session = Session.getDefaultInstance(properties);
		final MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(fromEmailAddresss));
		if (!toEmailAddress.isEmpty()) {
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));
		}
		if (!ccEmailAddress.isEmpty()) {
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmailAddress));
		}
		message.setSubject(emailSubject);
		message.setContent(emailContent, "text/html");

		Transport.send(message);
		return true;
	}
	
	
	/**
	 * Divide a large list into smaller chunks.
	 * 
	 * @param dataList - Input List to be split
	 * @param chunksize - Size of sub-list
	 * @param isBackedList - Specify if the returned List is required to be backed by input dataList
	 * @return A List containing sub List of the data split into chunks.
	 */
	public static List<List<Object>> splitListObject(List<Object> dataList,
			int chunksize, boolean isBackedList){
		List<List<Object>> splitList = new ArrayList<List<Object>>();
		for (int i = 0; i < dataList.size(); i = i + chunksize) {
			int size = Math.min(chunksize, (dataList.size()-i));
			List subList = dataList.subList(i, i+size);
			if (isBackedList) {
				splitList.add(subList);
			}else {
				splitList.add(new ArrayList(subList));
			}
		}
		return splitList;
	}
	
	/**
	 * Reads a specific column from an Excel file and convert it to List<String>
	 * 
	 * @param fileName - Absolute Filename of the input Excel file
	 * @param sheetName - Name of the sheet to be read in Excel workbook
	 * @param columnNumber - Column number of the column to be read(index starts with 0)
	 * @return List of String values of the column specified in input file.
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<String> readExcelDataAsListObject(String fileName, String sheetName, int columnNumber)
			throws IOException, InvalidFormatException {
		Sheet sheet = getExcelSheet(fileName, sheetName);
		Iterator<Row> iter = sheet.iterator();
		List resultList = new ArrayList();
		while (iter.hasNext()) {
			Row row = (Row) iter.next();
			Cell cell = row.getCell(columnNumber);
			resultList.add(cell.getStringCellValue());
		}
		return resultList;
	}
	
	/**
	 * Gets the Sheet object of the specified Excel workbook.
	 * 
	 * @param fileName - Absolute Filename of the input Excel file
	 * @param sheetName - Name of the sheet to be read in Excel workbook
	 * @return Sheet object view of the Excel sheet specified
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static Sheet getExcelSheet(String fileName, String sheetName) throws IOException, InvalidFormatException {
		File file = new File(fileName);
		Workbook workbook = null;
		Sheet sheet = null;

		if (fileName.endsWith("xls")) {
			NPOIFSFileSystem poiFS = new NPOIFSFileSystem(file);
			workbook = new HSSFWorkbook(poiFS);
			sheet = workbook.getSheet(sheetName);
		} else {
			OPCPackage opc = OPCPackage.open(file);
			XSSFWorkbook xWorkbook = new XSSFWorkbook(opc);
			sheet = xWorkbook.getSheet(sheetName);
		}
		return sheet;
	}
	
	/**
	 * Checks if the input string is a number.
	 * 
	 * @param input - String input to be verified
	 * @return true if the input is a number.
	 */
	public static boolean checkIfInputDataIsNumeric(String input) {
		//if(input!=null) return(input.matches("-?\\d+(\\.\\d+)?"));
		try {
			Double.parseDouble(input);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	
	/**
	 * Restrict decimal scale of the input.
	 * 
	 * @param input - Integer string to be roundoff
	 * @param roundoffIndex - Number of digits after decimal point
	 * @return String containing the number after scaling.
	 */
	public static String roundOffDecimals(String input, int roundoffIndex) {
		Double doubleValue = Double.parseDouble(input);
		BigDecimal bdValue = BigDecimal.valueOf(doubleValue);
		String result = bdValue.setScale(roundoffIndex, RoundingMode.HALF_DOWN).toString();

		return result;
	}

}
