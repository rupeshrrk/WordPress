package ExcelExportFileandIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ReadExcelFile {

	@Test
	public Object[][] readExcel(String filePath, String fileName, String sheetName) throws IOException {

		Object object[][] = null;

		File file = new File(filePath + "\\" + fileName);

		FileInputStream inputStream = new FileInputStream(file);

		XSSFWorkbook sample_workbook = new XSSFWorkbook(inputStream);

		XSSFSheet sampleSheet = sample_workbook.getSheet(sheetName);

		int rows = sampleSheet.getLastRowNum() - sampleSheet.getFirstRowNum();
		int columns = sampleSheet.getRow(1).getLastCellNum();

		System.out.println(columns);

		object = new Object[rows][columns];

		for (int i = 0; i < rows; i++) {
			// Loop over all the rows
			Row row = sampleSheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < columns; j++) {
				// Print excel data in console
				object[i][j] = (row.getCell(j) + "").toString();
				// System.out.println(object[i][j].toString());
			}
		}

		System.out.println("-----------------");
		return object;

	}

}
