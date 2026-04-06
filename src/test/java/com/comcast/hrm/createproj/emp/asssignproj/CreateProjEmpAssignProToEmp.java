package com.comcast.hrm.createproj.emp.asssignproj;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.comcast.hrm.generic.baseclass.BaseClass;
import com.comcast.hrm.generic.file.utility.ExcelUtility;
import com.comcast.hrm.objectrepository.CommonElements;
import com.comcast.hrm.objectrepository.CreateEmployeePage;
import com.comcast.hrm.objectrepository.CreateProjectPage;
import com.comcast.hrm.objectrepository.HomePage;
import com.comcast.hrm.objectrepository.ProjectsPage;
import com.comcast.hrm.threadlocal.DriverManager;

@Listeners(com.comcast.hrm.listener.ListenerClassImplementation.class)
public class CreateProjEmpAssignProToEmp extends BaseClass {
	@Test
	public void createProjAndEmp() throws EncryptedDocumentException, IOException, InterruptedException {

		System.out.println("Hi");
		HomePage hp = new HomePage(driver);
		hp.getProjectsLink().click();
		ExcelUtility excutil = new ExcelUtility();
		String projectName = excutil.getDataFromExcel("proj", 1, 0);
		String managerName = excutil.getDataFromExcel("proj", 1, 1);
		String projectStatus = excutil.getDataFromExcel("proj", 1, 2);

		ProjectsPage projp = new ProjectsPage(driver);
		projp.getCreateProjBtn().click();

		CreateProjectPage crprojp = new CreateProjectPage(driver);
		String projectNameUpdated = crprojp.createProject(projectName, managerName, driver, projectStatus);

		CommonElements ce = new CommonElements(driver);
		boolean projCreated = ce.verify(projectNameUpdated);

		Assert.assertEquals(projCreated, true);
		if(projCreated == true)
		{
			DriverManager.getTest().log(Status.PASS,"project is created");
		}
		else {
			DriverManager.getTest().log(Status.FAIL, "project not created");
		}

		hp.getEmployeesLink().click();

		CreateEmployeePage cremp = new CreateEmployeePage(driver);
		cremp.getCreateEmpLink().click();

		cremp.CreateEmpAndAssignProject(driver);
		
		CommonElements ce1 = new CommonElements(driver);
		boolean empCreated = ce1.verify(projectNameUpdated);

		Assert.assertEquals(empCreated, true);
		if(empCreated == true)
		{
			DriverManager.getTest().log(Status.PASS,"Employee is created");
		}
		else {
			DriverManager.getTest().log(Status.FAIL, "Employee is not created");
		}

	}
}
