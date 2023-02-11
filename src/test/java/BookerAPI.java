import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static files.payload.createBookingBody;
import static io.restassured.RestAssured.given;

public class BookerAPI {

    @Test
    public void PingCheckTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        //given().when().get("/ping");
        //given().log().all().when().get("/ping");
        given().log().all()
                .when().get("/ping")
                .then().log().all()
                .assertThat().statusCode(201);

    }

    @Test
    public void GetAllBookings() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .when().get("/booking/`")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void CreateBooking() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"firstname\" : \"Dragoljub\",\n" +
                        "    \"lastname\" : \"Boranijasevic\",\n" +
                        "    \"totalprice\" : 99,\n" +
                        "    \"depositpaid\" : false,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2022-01-01\",\n" +
                        "        \"checkout\" : \"2023-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Dinner\"\n" +
                        "}")
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void CreateBookingWithPayload() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        given().log().all()
                .header("Content-Type", "application/json")
                .body(createBookingBody())
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void CreateBookingJson() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String response;

        response = given().log().all()
                .header("Content-Type", "application/json")
                .body(createBookingBody())
                .when().post("/booking")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println("RESPONSE: " + response);

        JsonPath js = new JsonPath(response);
        String bookingid = js.getString("bookingid");
        System.out.println("BOOKING ID: "+bookingid);
        Assert.assertTrue(response.contains(bookingid));

        String firstname = js.getString("booking.firstname");
        String lastname = js.getString("booking.lastname");
        Assert.assertTrue(response.contains(firstname));
        Assert.assertTrue(response.contains(lastname));
        System.out.println("Firstname: " + firstname);




    }

}