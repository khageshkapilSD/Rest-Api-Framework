package main.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.model.Get;
import api.model.LoginCredentials;
import api.model.Page;
import api.model.Token;
import main.master.ServiceApi;

public class VerifyPostLoginCredentials {
	
	@Test(groups = { "loginTests", "happyPathTests" })
	public void verifySuccessfullLogin() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ServiceApi serviceApi = new ServiceApi("postLoginCredentials");
		LoginCredentials loginCredentials = new LoginCredentials();
		loginCredentials.setEmail("sydney@fife");
		loginCredentials.setPassword("pistol");
		serviceApi.hit(loginCredentials);
		Token token = (Token) serviceApi.getResponseModel();
		Assert.assertNotNull(token.getToken() , "Token should not be null.");
		Assert.assertTrue(!token.getToken().equals("") , "Token should not be empty.");
	}
	
	@Test(groups = { "loginTests", "negativeTests" })
	public void verifyInvalidLogin() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ServiceApi serviceApi = new ServiceApi("postLoginCredentials");
		LoginCredentials loginCredentials = new LoginCredentials();
		loginCredentials.setEmail("sydney@fife");
		serviceApi.hit(loginCredentials);
		Token token = (Token) serviceApi.getResponseModel();
		Assert.assertNull(token.getToken() , "Token should be null.");
		Assert.assertEquals(token.getError(), "Missing password", "Error should be \"Missing Password\"");
	}

	@Test(groups = { "loginTests", "negativeTests" })
	public void verifyEmptyLogin() throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ServiceApi serviceApi = new ServiceApi("postLoginCredentials");
		LoginCredentials loginCredentials = new LoginCredentials();
		serviceApi.hit(loginCredentials);
		Token token = (Token) serviceApi.getResponseModel();
		Assert.assertNull(token.getToken() , "Token should be null.");
		Assert.assertEquals(token.getError(), "Missing email or username" , "Error should be \"Missing email or username\"");
	}
	
}
