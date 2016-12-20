package main.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import api.model.Get;
import api.model.LoginCredentials;
import api.model.Page;
import api.model.Token;
import main.master.ServiceApi;

public class Testing {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		ServiceApi serviceApi = new ServiceApi("getUsersPage");
//		Get getModel = new Get();
//		getModel.addRequestParameter("page","1");
//		serviceApi.hit(getModel);
//		Page page = (Page) serviceApi.getResponseModel();
		
		serviceApi = new ServiceApi("postLoginCredentials");
		LoginCredentials loginCredentials = new LoginCredentials();
		loginCredentials.setEmail("sydney@fife");
		//loginCredentials.setPassword("pistol");
		serviceApi.hit(loginCredentials);
		Token token = (Token) serviceApi.getResponseModel();
	}
}
