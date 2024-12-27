package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndpoints2;
import api.payloads.User;
import io.restassured.response.Response;

//request body have these fields
//"id": 0,
// "username": "string",
// "firstName": "string",
// "lastName": "string",
// "email": "string",
// "password": "string",
// "phone": "string",
// "userStatus": 0

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setupData()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPassword(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*************Creating User***********");
		Response response=userEndpoints2.createUser(userPayload);
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("************* User is created ***********");
	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("*************reading User***********");
		Response response=userEndpoints2.readUser(this.userPayload.getUsername());
		
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************reading User completed***********");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("*************updating User***********");
		//before this update some data and whichever data we are updating that just add here
		//add except username because username we are taking as inputs in this
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		
		
		Response response=userEndpoints2.updateUser(userPayload,this.userPayload.getUsername());
		
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//validation after update
		
		Response responseAfterUpdate=userEndpoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("*************updating User completed***********");
	}
	
	@Test(priority=4)
	public void TestDeleteUserByName()
	{
		logger.info("*************deleing User***********");
		Response response=userEndpoints2.deleteUser(this.userPayload.getUsername());
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*************deleting User completed***********");
	}

}
