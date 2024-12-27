package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.userEndpoints;
import api.payloads.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {

	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String userid,String userName , String fname ,String lname, String useremail, String pwd, String ph)
	{
		User userPayload=new User();
		
		userPayload.setId(Integer.parseInt(userid));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPassword(ph);
		
		
		Response response=userEndpoints.createUser(userPayload);
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Test(priority=2,  dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void TestDeleteUserByName(String userName)
	{
		Response response=userEndpoints.deleteUser(userName);
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
