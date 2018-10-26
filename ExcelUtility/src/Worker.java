import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Worker {
	public String plan = "XYZ";
	public String date = "12/12/2121";

	/**
	 * To read the schema Excel file as a list to be iterated.
	 * 
	 * @param fileName
	 * @param sheetName
	 * @return List view of the WorkbookName, SheetName, PlanColumn, EndDateColumn
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public List ReadSchemaFile(String fileName, String sheetName) throws InvalidFormatException, IOException {
		Sheet sheet = (Sheet)ExcelReader.getExcelSheet(fileName, sheetName)[1];
		Iterator<Row> iter = sheet.iterator();
		List resultList = new ArrayList();
		iter.hasNext();
		while (iter.hasNext()) {
			String[] schemaDetail = new String[4];
			Row row = (Row) iter.next();
			for (int i = 0; i < 4; i++) {
				Cell cell = row.getCell(i);
				if("STRING".equals(cell.getCellTypeEnum().name()))
					schemaDetail[i] = cell.getStringCellValue();
				else if ("NUMERIC".equals(cell.getCellTypeEnum().name())) {
					schemaDetail[i] = String.valueOf(cell.getNumericCellValue());
				}
			}
			resultList.add(schemaDetail);
		}
		return resultList;
	}
	
	public String Execute(List<String[]> schema) throws InvalidFormatException, IOException{
		String result = "";
		Iterator<String[]> iter = schema.iterator();
		iter.next();
		while (iter.hasNext()) {
			String[] schemaDetail = iter.next();
			String workbook = schemaDetail[0];
			String sheetName = schemaDetail[1];
			int planColumnNumber = Integer.parseInt(Worker.roundOffDecimals(schemaDetail[2], 0));
			int EDColNumber = Integer.parseInt(Worker.roundOffDecimals(schemaDetail[3], 0));
			Object[] ob = ExcelReader.getExcelSheet(workbook, sheetName);
			Sheet sheet = (Sheet)ob[1];
			Iterator<Row> iter1 = sheet.iterator();
			while (iter1.hasNext()) {
				Row row = (Row) iter1.next();
				Cell cell = row.getCell(planColumnNumber);
				if(plan.equals(cell.getStringCellValue())){
					Cell cell1 = row.getCell(EDColNumber);
					cell1.setCellValue(date);
					result = result + ("Moified " + workbook+ " :: "+ sheetName +" :: "+ cell1.getRowIndex()+1+"::"+cell1.getColumnIndex()+1 +"\n");
				}
			}
			System.out.println(workbook);
			try{
				File file = (File)ob[0];
				InputStream is = (InputStream)ob[2];
				is.close();
				System.out.println(file.canWrite());
				Workbook book = sheet.getWorkbook();
				FileOutputStream out = new FileOutputStream(file);
				book.write(out);
				out.close();
				book.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
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
