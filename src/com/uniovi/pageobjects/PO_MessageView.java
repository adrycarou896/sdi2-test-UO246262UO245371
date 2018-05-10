package com.uniovi.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_MessageView extends PO_NavView {
	
	static public void fillForm(WebDriver driver, String searchtextp) {
		
			WebElement searchtext = driver.findElement(By.id("agregar-texto"));
			searchtext.click();
			searchtext.clear();
			searchtext.sendKeys(searchtextp);
			
			By boton = By.id("boton-agregar");
			driver.findElement(boton).click();

		}
}
