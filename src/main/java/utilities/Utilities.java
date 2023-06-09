package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.javafaker.Faker;

public class Utilities {

	public static final int wait = 10;

	public static ExtentReports generateExtentReport() {

		ExtentReports extent = new ExtentReports();

		File path = new File(System.getProperty("user.dir") + "\\test-output\\ExtentReport\\extentreport.html");
		ExtentSparkReporter spark = new ExtentSparkReporter(path);

		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("TimeLogger Test Report");
		spark.config().setReportName("TimeLogger Test Suite");

		extent.attachReporter(spark);
		Properties prop = configData();
		extent.setSystemInfo("Application URL", prop.getProperty("url"));
		extent.setSystemInfo("Agency Email Address", prop.getProperty("validEmail"));
		extent.setSystemInfo("Password ", prop.getProperty("validPass"));
		extent.setSystemInfo("Browser Name", prop.getProperty("Browser"));
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("System Version", System.getProperty("os.version"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));

		return extent;
	}

	public static Properties configData() {

		Properties prop = new Properties();
		File path = new File(System.getProperty("user.dir") + "\\src\\main\\java\\config\\config.properties");
		try {
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return prop;
	}

	public static Properties testData() {

		Properties dataprop = new Properties();
		File path = new File(System.getProperty("user.dir") + "\\src\\main\\java\\testdata\\testdata.properties");
		try {
			FileInputStream fis = new FileInputStream(path);
			dataprop.load(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return dataprop;
	}

	public static String screenShot(WebDriver driver, String testname) {

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\Screenshot\\" + testname + ".png";
		try {
			FileHandler.copy(src, new File(destination));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return destination;
	}

	public static String[][] generateRandom() {

		Faker faker = new Faker(new Locale("en-PAK"));

		String email = faker.name().username() + "@gmail.com";
		String password = faker.name().username();
		String[][] data = {{email,password}};

		return data;

	}

	public static Object[][] validDataSupplyEmailAndPassword(String sheetName) {

		File path = new File(
				System.getProperty("user.dir") + "\\src\\main\\java\\testdata\\ValidEmails.xlsx");
		XSSFWorkbook workbook = null;
		try {
		FileInputStream fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int rows = sheet.getLastRowNum();
		int cols = sheet.getRow(1).getLastCellNum();
		
		Object[][] data = new Object[rows][cols];
		for(int i=0; i<rows; i++) {
			XSSFRow row = sheet.getRow(i+1);
			for(int j=0; j<cols; j++) {
				XSSFCell cell = row.getCell(j);
				CellType cellType = cell.getCellType();
				switch(cellType) {
				
				case STRING:
					data[i][j] = cell.getStringCellValue();
					break;
					
				case NUMERIC:
					data[i][j] = Integer.toString((int)cell.getNumericCellValue());
					break;
 				}
			}
		}
		return data;
	}
}
