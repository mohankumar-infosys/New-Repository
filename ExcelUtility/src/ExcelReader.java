import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelReader {
	
	/**
	 * Gets the Sheet object of the specified Excel workbook.
	 * 
	 * @param fileName - Absolute Filename of the input Excel file
	 * @param sheetName - Name of the sheet to be read in Excel workbook
	 * @return Sheet object view of the Excel sheet specified
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static Object[] getExcelSheet(String fileName, String sheetName) throws IOException, InvalidFormatException {
		File file = new File(fileName);
		Workbook workbook = null;
		Sheet sheet = null;
		InputStream is = new FileInputStream(file);
		
		if (fileName.endsWith("xls")) {
			//NPOIFSFileSystem poiFS = new NPOIFSFileSystem(file);
			
			workbook = new HSSFWorkbook(is);
			sheet = workbook.getSheet(sheetName);
		} else {
			//OPCPackage opc = OPCPackage.open(file);
			XSSFWorkbook xWorkbook = new XSSFWorkbook(is);
			sheet = xWorkbook.getSheet(sheetName);
		}

		return new Object[]{file, sheet, is};
	}

}
