package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SearchTextViewCliente extends PO_NavView {
	
	static public void fillForm(WebDriver driver, String searchtextp) {
		
			WebElement searchtext = driver.findElement(By.id("filtro-email"));
			searchtext.click();
			searchtext.clear();
			searchtext.sendKeys(searchtextp);

		}
}
