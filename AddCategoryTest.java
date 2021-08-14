package com.tradeviv.testCases;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tradeviv.pageObjects.AddChildCategory;
import com.tradeviv.pageObjects.AddMainCategory;
import com.tradeviv.pageObjects.AddSubCategory;
import com.tradeviv.pageObjects.AdminLoginPage;

public class AddCategoryTest extends BaseClass{

	@BeforeClass(alwaysRun=true)
	public void setup2()
	{
		AdminLoginPage alp=new AdminLoginPage(driver);
		alp.setAdminId(username);
		alp.setAdminPwd(password);
		alp.clickSubmit();
		
		AddChildCategory acc=new AddChildCategory(driver);
		acc.clickManageCategory();
	}
	
	@Test(dataProvider="category-test data")
	public void addCategory(String main_cat, String sub_cat, String child_cat)
	{
		AddChildCategory acc=new AddChildCategory(driver);
		
		acc.clickChildCategory();
		acc.addNewChild();
		acc.mainDropClick();
		if(acc.getMainOptions().contains(main_cat))
		{
			acc.selectMainCategory(main_cat);
		}
		else
		{
			acc.closePopup();
			addMainCategory(main_cat);
			addSubCategory(main_cat, sub_cat);
		}
		
		if(acc.getSubOptions().contains(sub_cat))
		{
			acc.selectSubCategory(sub_cat);
		}
		else
		{
			acc.closePopup();
		}
		
		
	}
	
	@DataProvider(name="category-test data")
	public String [][] getData() throws IOException
	{
		//get the data from excel
		String path=System.getProperty("user.dir")+"/src/test/java/com/tradeviv/testData/demoCategory1.xlsx";
		String[][] subCatData=dataProvider(path);
		return subCatData;
	}
	
	public void addMainCategory(String main_cat)
	{
		AddMainCategory amc=new AddMainCategory(driver);
		
		amc.mainCategoryClick();
		amc.createMainCategory();
		amc.mainCategoryField(main_cat);
		String slug=main_cat.replaceAll("\\s", "").toLowerCase();
		amc.slugField(slug);
		String imagepath=System.getProperty("user.dir")+"\\src\\test\\java\\com\\tradeviv\\testData\\main category.jpg";
		amc.mainImgUpload(imagepath);
		amc.submitMainCategory();
	}
	
	public void addSubCategory(String main_cat, String child_cat)
	{
		AddSubCategory asc=new AddSubCategory(driver);
		asc.subCategoryClick();
	}
	
}
