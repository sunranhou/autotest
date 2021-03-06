package com.accenture.autotest.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.accenture.autotest.service.ActionHandle;
import com.accenture.autotest.util.ScreenUtil;

public class VerifyDropdownlistActionHandle extends ActionHandle {
	private static Logger logger = Logger.getLogger(VerifyDropdownlistActionHandle.class);

	public boolean handle(WebDriver driver, String sheetName, List<String> rowData) {
		boolean res = false;
		try {
			String xpath = this.getXpath();
//			WebDriverWait wait = new WebDriverWait(driver,30);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			WebElement webElement = driver.findElement(By.xpath(xpath));
			
			if (webElement != null) {
			    Select selector = new Select(webElement);
//				String value = selector.getFirstSelectedOption().getAttribute("value");
			    
			    //
				String pattern = this.getValue();		
			    String[] arrayPattern =new String[]{};
			    arrayPattern = pattern.split(",");
				
				List<WebElement> options = selector.getOptions();
				boolean match = false;
				if(arrayPattern.length == options.size()){
				    for (int i=0; i<arrayPattern.length; i++){
	                    for (WebElement we:options){
	                          if (we.getText().equals(arrayPattern[i])){
	                              match = true;
	                          }
	                    }

	                    if (!match) {
	                        logger.info(">>expected content:" + arrayPattern[i]);
	                        logger.info(">>actual content: ");
	                        break;
	                    }
	                }
				}

				logger.info(">>matcher result:" + match);
				
                if (match) {				
					ScreenUtil.highlightElement(driver, webElement);
					ScreenUtil.captureScreen(this.getPrintPath(true), driver);
    				res = true;
                } else {
                    this.errorMessageHandle(">>sheet:" + sheetName + ",VerifySelectedValueActionHandle fail, content doesn't exist:" + pattern);                 
                    ScreenUtil.captureScreen(this.getPrintPath(false), driver);                    
                }
			} else {
				this.errorMessageHandle(">>sheet:" + sheetName + ",can not find xpath:"	+ xpath);
			}
		} catch (Exception ex) {
			this.errorMessageHandle(ex);
		}
		return res;

	}
}
