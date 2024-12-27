package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import org.testng.annotations.Test;

import api.payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

// To perform CRUD operations-Create, Read, Update, Delete
//get data from properties file
public class userEndpoints2 {

	static ResourceBundle getURL()
	{
		ResourceBundle routes=ResourceBundle.getBundle("Routes");  //load properties file//routes is name of property file no need of extension
		return routes;
	}
	
	public static Response createUser(User payload)
	{
		String post_URL=getURL().getString("post_URL");
		
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		    .body(payload)
		
		.when()
			.post(post_URL);
		
		return response;
	}
	
	public static Response readUser(String username)
	{
		String get_URL=getURL().getString("get_URL");
		Response response=given()
			 .pathParam("username", username)
		
		.when()
			.get(get_URL);
		
		return response;
	}

	public static Response updateUser(User payload, String username)
	{
		String put_URL=getURL().getString("put_URL");
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", username)
		    .body(payload)
		
		.when()
			.put(put_URL);
		
		return response;
	}
	
	public static Response deleteUser(String username)
	{
		String delete_URL=getURL().getString("delete_URL");
		Response response=given()
			 .pathParam("username", username)
		
		.when()
			.delete(delete_URL);
		
		return response;
	}
}
