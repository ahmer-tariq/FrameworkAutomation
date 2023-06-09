package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.Base;
import utilities.Utilities;
import webpages.AccountPage;
import webpages.LoginPage;

public class Logintest extends Base {

	public WebDriver driver;
	LoginPage loginpage;
	AccountPage account;

	@BeforeMethod
	@Parameters("Browser")
	public void startup(@Optional("edge") String browser) {
		driver = launchApplication(browser);
		}

	@Test(priority = 1)
	public void validEmailAndValidPassword() throws Throwable {

		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(prop.getProperty("validEmail"), prop.getProperty("validPass"));
		Thread.sleep(3000);
		Assert.assertTrue(account.verifyLogoutButton());

	}

	@Test(priority = 2)
	public void validEmailAndInvalidPassword() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.emailAndPasswordField(prop.getProperty("validEmail"), dataprop.getProperty("invalidPass"));
		Thread.sleep(5000);
		Assert.assertTrue(loginpage.verifyHelloAgain());
	}

	@Test(priority = 3)
	public void invalidEmailValidPassword() throws Throwable {
		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(dataprop.getProperty("invalidEmail"), prop.getProperty("validPass"));
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyHelloAgain());
	}

	@Test(priority = 4, dataProvider = "Invaliddata")
	public void invalidEmailAndInvalidPassword(String email, String pass) throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.emailAndPasswordField(email, pass);
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyHelloAgain());
	}

	@DataProvider(name = "Invaliddata")
	public String[][] data() {

		String[][] data = Utilities.generateRandom();
		return data;
	}

	@Test(priority = 5)
	public void onlyValidEmail() throws Throwable {

		loginpage = new LoginPage(driver);
		loginpage.emailField(prop.getProperty("validEmail"));
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyPasswordRequiredText());

	}

	@Test(priority = 6)
	public void onlyValidPassword() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.passwordField(prop.getProperty("validPass"));
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyEmailRequiredText());
	}

	@Test(priority = 7)
	public void onlyInvalidAbnormalEmail() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.emailField(dataprop.getProperty("invalidEmailAbnormal"));
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyPasswordRequiredText() && loginpage.enterValidEmail());
	}

	@Test(priority = 8)
	public void checkCrossEyeIcon() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.crossEyeIcon(dataprop.getProperty("invalidEmailAbnormal"));
		Assert.assertTrue(loginpage.verifyEmailRequiredText());
	}

	@Test(priority = 9)
	public void bothFieldsEmpty() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.clickEmailAndPasswordField();
		Thread.sleep(3000);
		Assert.assertTrue(loginpage.verifyEmailRequiredText() && loginpage.verifyPasswordRequiredText());
	}

	@Test(priority = 10)
	public void signInButton() {
		loginpage = new LoginPage(driver);
		Assert.assertFalse(loginpage.signInButton());
	}

	@Test(priority = 11)
	public void AbnormalEmailAndInvalidPassword() throws Throwable {
		loginpage = new LoginPage(driver);
		loginpage.emailAndPasswordField(dataprop.getProperty("invalidEmailAbnormal"),
				dataprop.getProperty("invalidPasswordAbnormal"));
		Thread.sleep(4000);
		Assert.assertTrue(loginpage.verifyHelloAgain());
	}

	@Test(priority = 12)
	public void validCredentialsAndRefresh() throws InterruptedException {
		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(prop.getProperty("validEmail"), prop.getProperty("validPass"));
		Thread.sleep(5000);
		driver.navigate().refresh();
		Assert.assertTrue(account.verifyLogoutButton());
	}

	@Test(priority = 13)
	public void validCredentialsAndMoveToNewTab() throws Throwable {

		loginpage = new LoginPage(driver);
		account = loginpage.validCredentialsWithNewTabAndRefreshPage(prop.getProperty("validEmail"),
				prop.getProperty("validPass"));
		Assert.assertTrue(account.verifyLogoutButton());

	}

	@Test(priority = 14)
	public void afterLoginAndLogoutGoBackVerifyTheLogoutSccessfull() throws Throwable {

		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(prop.getProperty("validEmail"), prop.getProperty("validPass"));
		Thread.sleep(4000);
		account.clickLogout();
		Thread.sleep(3000);
		driver.navigate().back();
		Thread.sleep(4000);
		System.out.println("The Title is :  " + driver.getCurrentUrl());
		try {
			Assert.assertTrue(loginpage.verifyHelloAgain());
		} catch (Throwable e) {
			Assert.fail();
		}

	}

	@Test(priority = 15)
	public void verifyURLBeforeAndAfterLogin() {
		loginpage = new LoginPage(driver);
		String BaseURL = prop.getProperty("url");
		loginpage.emailAndPasswordField(prop.getProperty("validEmail"), prop.getProperty("validPass"));
		String AfterLoginURL = driver.getCurrentUrl();
		Assert.assertEquals(BaseURL, AfterLoginURL);
	}

	@Test(priority = 16, dataProvider = "validEmails")
	public void verifyLoginWithValidCredentialsMultipleTimes(String email, String pass) throws Throwable {
		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(email, pass);
		Thread.sleep(4000);
		loginpage = account.clickLogout();
		Assert.assertTrue(loginpage.verifyHelloAgain());
	}

	@Test(priority = 17)
	public void loginWithValidCredentialsAndBackwardAndForward() throws Throwable {
		loginpage = new LoginPage(driver);
		account = loginpage.emailAndPasswordField(prop.getProperty("validEmail"), prop.getProperty("validPass"));
		Thread.sleep(2000);
		driver.navigate().back();
		driver.navigate().forward();
		Assert.assertTrue(account.verifyLogoutButton());
	}

	@DataProvider(name = "validEmails")
	public Object[][] validEmailsAndPassword() {
		Object[][] data = Utilities.validDataSupplyEmailAndPassword("Login");
		return data;
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}
