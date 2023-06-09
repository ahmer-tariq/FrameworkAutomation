package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	@FindBy(xpath = "//input[@name='email']")
	private WebElement emailField;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement passField;

	@FindBy(xpath = "//label[contains(text(),'Remember Me?')]")
	private WebElement rememberMe;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	private WebElement signInButton;

	@FindBy(xpath = "//a[contains(text(), ' Dont have an account? Sign Up    ')]")
	private WebElement signUpLink;

	@FindBy(xpath = "//img[contains(@src,'images/PasswordEye.svg')]")
	private WebElement eyeIcon;

	@FindBy(xpath = "//h1[normalize-space()='Hello Again!']")
	private WebElement HelloAgain;

	@FindBy(xpath = "//img[@src='images/EmailEnvolap.svg']")
	private WebElement emailIcon;

	@FindBy(xpath = "//p[contains(text(),'Password is Required')]")
	private WebElement passwordRequiredText;

	@FindBy(xpath = "//p[contains(text(),'Please Enter Your Email')]")
	private WebElement emailRequiredText;

	@FindBy(xpath = "//p[contains(text(),'Please enter valid email')]")
	private WebElement enterValidEmailText;

	@FindBy(xpath = "//img[@src='images/PasswordCrossEye.svg']")
	private WebElement crossEyeIcon;

	@FindBy(xpath = "//img[@src='images/PasswordEye.svg']")
	private WebElement passwordEye;

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public AccountPage emailAndPasswordField(String email, String Password) {
		emailField.sendKeys(email);
		passField.sendKeys(Password);
		signInButton.click();
		return new AccountPage(driver);
	}

	public boolean verifyHelloAgain() {

		boolean display = HelloAgain.isDisplayed();
		return display;
	}

	public void emailField(String email) {
		emailField.sendKeys(email);
		passField.click();
		signInButton.click();
	}

	public boolean verifyPasswordRequiredText() {
		boolean text = passwordRequiredText.isDisplayed();
		return text;
	}

	public boolean verifyEmailRequiredText() {
		boolean text = emailRequiredText.isDisplayed();
		return text;
	}

	public void passwordField(String Password) {
		passField.sendKeys(Password);
		signInButton.click();
	}

	public boolean enterValidEmail() {
		boolean text = enterValidEmailText.isDisplayed();
		return text;
	}

	public void crossEyeIcon(String password) throws Throwable {
		crossEyeIcon.click();
		passField.sendKeys(password);
		Thread.sleep(4000);
		passwordEye.click();
		Thread.sleep(3000);
		signInButton.click();
	}

	public void clickEmailAndPasswordField() {
		emailField.click();
		passField.click();
		signInButton.click();
	}
	
	public boolean signInButton() {
		boolean enable = signInButton.isEnabled();
		return enable;
	}
	
	public AccountPage validCredentialsWithNewTabAndRefreshPage(String email, String Pass) throws Throwable {
		emailField.sendKeys(email);
		passField.sendKeys(Pass);
		signInButton.click();
		Thread.sleep(4000);
		String url = driver.getCurrentUrl();
		driver.switchTo().newWindow(WindowType.TAB);
		driver.navigate().to(url);
		return new AccountPage(driver);
	}
}
