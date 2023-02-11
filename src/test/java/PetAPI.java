import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojoPet.bodyUser;

import static io.restassured.RestAssured.given;

public class PetAPI {
    @Test
    public void creatUser(){
        bodyUser createUserBody = new bodyUser();
        createUserBody.getId();
        createUserBody.setUsername("gags");
        createUserBody.setFirstName("Dragana");
        createUserBody.setLastName("Topic");
        createUserBody.setEmail("aaa@gmail.com");
        createUserBody.setPassword("gags123");
        createUserBody.setPhone("06423165");
        createUserBody.getUserStatus();
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        String response;

        response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createUserBody)
                .log().all()
                .post("/user")
                .then()
                .assertThat().statusCode(200)
         .extract().response().asString();
        System.out.println(response);
    }
    @Test
    public void getUser(){
        creatUser();
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        given().log().all()

                .when().get("/user/gags")
                .then().log().all().
                assertThat().statusCode(200).
                extract().response().asString();
    }

    @Test
    public void getUserDoesntExist(){
        creatUser();
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
        given().log().all()

        .when().get("/user/user1")
        .then().log().all().
        assertThat().statusCode(404).
        extract().response().asString();
    }
@Test
    public void getUserLogin(){
            creatUser();
            RestAssured.baseURI = "https://petstore.swagger.io/v2";
            String response = given().log().all()
            .when().queryParam("username", "gags").queryParam("password", "gags123").get("/login")
            .then().log().all()
            .assertThat().statusCode(200).extract().response().asString();
             System.out.println(response);
}
@Test
    public void deleteUser(){
    creatUser();
    RestAssured.baseURI = "https://petstore.swagger.io/v2";
    String response = given().log().all()
    .header("api_key", "special-key")
    .delete("/user/user1")
    .then().log().all().assertThat().statusCode(404)
    .extract().response().asString();
     System.out.println(response); 
}

}
