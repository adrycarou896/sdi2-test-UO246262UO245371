package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_SearchTextView extends PO_NavView {
	
	static public void fillForm(WebDriver driver, String searchtextp) {
		
			WebElement searchtext = driver.findElement(By.name("searchText"));
			searchtext.click();
			searchtext.clear();
			searchtext.sendKeys(searchtextp);

		
			By boton = By.className("btn");
			driver.findElement(boton).click();
		}
}
