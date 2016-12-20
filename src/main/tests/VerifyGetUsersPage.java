package main.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.model.Get;
import api.model.Page;
import main.master.ServiceApi;

public class VerifyGetUsersPage {

	@Test(groups = { "searchTests", "happyPathTests" })
	public void verifyPageNumber() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ServiceApi serviceApi = new ServiceApi("getUsersPage");
		Get getModel = new Get();
		getModel.addRequestParameter("page","1");
		serviceApi.hit(getModel);
		Page page = (Page) serviceApi.getResponseModel();
		Assert.assertEquals(page.getPage(), new Integer(1) , "Page number should be as requested"); 
	}
	
	@Test(groups = { "searchTests", "happyPathTests" })
	public void verifyPerPageResults() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ServiceApi serviceApi = new ServiceApi("getUsersPage");
		Get getModel = new Get();
		getModel.addRequestParameter("page","1");
		serviceApi.hit(getModel);
		Page page = (Page) serviceApi.getResponseModel();
		Assert.assertEquals(page.getPer_page(), new Integer(page.getData().size()) , "Size of data should be equal to Per page attribute");
	}
}
