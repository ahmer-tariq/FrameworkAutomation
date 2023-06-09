package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountPage {
	
	WebDriver driver;

	@FindBy ( xpath = "//h1[contains(text(),'Log out')]" )
	private WebElement Logout;
	
	
	@FindBy ( xpath = "//p[contains(text(),'You must sign in to view this ')]" )
	private WebElement accountText;
	
	
	public AccountPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean verifyLogoutButton() {
		boolean logout = Logout.isDisplayed();
		return logout;
	}
	
	public Boolean verifyAccountText() {
		
		Boolean text = accountText.isDisplayed();
		return text;
	}
	
	public LoginPage clickLogout() {
		Logout.click();
		return new LoginPage(driver);
	}
}
