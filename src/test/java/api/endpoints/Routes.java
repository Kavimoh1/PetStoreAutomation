package api.endpoints;



//      Swagger  --https://petstore.swagger.io/
//      Post   -   https://petstore.swagger.io/v2/user
//      Get  -  https://petstore.swagger.io/v2/user/{username}	
//		Put  -  https://petstore.swagger.io/v2/user/{username}	
//		Delete  -https://petstore.swagger.io/v2/user/{username}
//		
public class Routes {
	
	
	public static String baseURL ="https://petstore.swagger.io/v2";
	
	
	//user modules url
	public static String post_URL = baseURL +"/user";
	public static String get_URL = baseURL +"/user/{username}";
	public static String put_URL = baseURL +"/user/{username}";
	public static String delete_URL = baseURL +"/user/{username}";

	//other modules url
}
