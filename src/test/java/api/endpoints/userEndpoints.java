package api.endpoints;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import api.payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

// To perform CRUD operations-Create, Read, Update, Delete
//get data from routes 
public class userEndpoints {

	
	public static Response createUser(User payload)
	{
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		    .body(payload)
		
		.when()
			.post(Routes.post_URL);
		
		return response;
	}
	
	public static Response readUser(String username)
	{
		Response response=given()
			 .pathParam("username", username)
		
		.when()
			.get(Routes.get_URL);
		
		return response;
	}

	public static Response updateUser(User payload, String username)
	{
		Response response=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", username)
		    .body(payload)
		
		.when()
			.put(Routes.put_URL);
		
		return response;
	}
	
	public static Response deleteUser(String username)
	{
		Response response=given()
			 .pathParam("username", username)
		
		.when()
			.delete(Routes.delete_URL);
		
		return response;
	}
}
