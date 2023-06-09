package listener;

import java.awt.Desktop;
import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.Utilities;

public class MyListener implements ITestListener {

	WebDriver driver = null;
	ExtentReports extent;
	ExtentTest extenttest;

	@Override

	public void onStart(ITestContext context) {
		extent = Utilities.generateExtentReport();
		
	}

	@Override
	public void onTestStart(ITestResult result) {
		extenttest = extent.createTest(result.getName());
		extenttest.log(Status.INFO, result.getName() + " Test Started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extenttest.log(Status.INFO, result.getName());
		extenttest.log(Status.PASS, result.getName() + " Test Successfull");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String destination = Utilities.screenShot(driver, result.getName());
		extenttest.addScreenCaptureFromPath(destination);

		extenttest.log(Status.INFO, result.getThrowable());
		extenttest.log(Status.FAIL, result.getName() + " Test Fails");
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		String destination = Utilities.screenShot(driver, result.getName());
		extenttest.addScreenCaptureFromPath(destination);
		extenttest.log(Status.INFO, result.getThrowable());
		extenttest.log(Status.SKIP, result.getName() + " Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {

		extent.flush();
		File path = new File(System.getProperty("user.dir") + "\\test-output\\ExtentReport\\extentreport.html");
		try {
			Desktop.getDesktop().browse(path.toURI());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
