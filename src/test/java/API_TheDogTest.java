
import com.sun.org.glassfish.gmbal.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;


public class API_TheDogTest {

    static final ResponseSpecification success = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    static final RequestSpecification request = new RequestSpecBuilder()
            .setBaseUri("https://api.thedogapi.com/")
            .addHeader("x-api-key", "live_MRsAphhQ9JPOjyPmcvz1x9FWgV2HvkKB0OyxfdrPkQb6uWUQ1I3yfML3gYWOubf1")
            .setAccept(ContentType.JSON)
            .build()
            .filter(new RequestLoggingFilter())
            .filter(new AllureRestAssured());
    @Test
    @Description("Test Description: To get the favourites for only the 'sub_id' you used when creating the Favourite via POST /favourites")
    public void GetFavouriteSubID() {
        given()
                .spec(request)
                .queryParam("sub-id", "my-user-1234")
                .when()
                .get("/v1/favourites")
                .then()
                .spec(success);
        System.out.println("The favourite SUB ID has been retrieved");
    }

    @Test
    public void GetFavouriteID() {
        given()
                .spec(request)
                .queryParam("favourite_id", "2000")
                .when()
                .get("v1/favourites/")
                .then()
                .spec(success);
        System.out.println("The favourite ID  has been retrieved");

    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description : Creating a favourite with a sub_id")
    public void CreateFavouriteWithSubId() {

        JSONObject data = new JSONObject();
        data.put("image_id", "Cre8er");
        data.put("sub_id", "my-user-1234");

        Response response = given().spec(request)
                .body(data)
                .contentType(ContentType.JSON)
                .when()
                .post("v1/favourites");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));


    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description : Saving a favourite to your account without a sub_id ")
    public void SaveFavouriteWithSubId() {

        JSONObject data = new JSONObject();
        data.put("image_id", "ggvhkj");

        Response response = given().spec(request)
                .body(data)
                .contentType(ContentType.JSON)
                .when()
                .post("v1/favourites");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));

    }

    @Test
    @Story("DELETE Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description : Deleting a favourite from your account")
    public void DeleteFavouriteFromAccount() {

        String favoriteId = "1708";

        Response response = given().spec(request)
                .pathParam("favorite_id", favoriteId)
                .when()
                .delete("v1/favourites/{favorite_id}");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));

    }

    @Test
    @Description("To get the votes for only the 'sub_id' you used when creating the Vote via POST /votes")
    public void GetVotesForSubID() {
        given()
                .spec(request)
                .queryParam("v1/votes", "my-user-1234")
                .when()
                .get("/v1/votes")
                .then()
                .spec(success);
        System.out.println("The votes for the sub_id have been retrieved");

    }

    @Test
    @Description("Retrieve an individual Vote by its ID")
    public void GetIDOfVote() {
        String voteId = "12345";
        given()
                .spec(request)
                .queryParam("vote_id", voteId)
                .when()
                .get("/v1/votes")
                .then()
                .spec(success);
        System.out.println("The Vote ID has been retrieved");

    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description :  a Down vote for image_id 'asf2'")
    public void DownVote() {

        JSONObject data = new JSONObject();
        data.put("image_id", "asf2");
        data.put("sub_id", "my-user-1234");
        data.put("value", "0");

        Response response = given().spec(request)
                .body(data)
                .contentType(ContentType.JSON)
                .when()
                .post("v1/votes");
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));

    }

    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description :  an UP vote for image_id 'asf2'")
    public void UpVote() {

        JSONObject data = new JSONObject();
        data.put("image_id", "asf2");
        data.put("sub_id", "my-user-1234");
        data.put("value", "1");

        Response response = given().spec(request)
                .body(data)
                .contentType(ContentType.JSON)
                .when()
                .post("v1/votes");
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));

    }

    @Test
    @Story("DELETE Request")
    @Severity(SeverityLevel.NORMAL)
    @io.qameta.allure.Description("Test Description : Delete vote id")
    public void DeleteVote() {

        String voteId = "36491";

        Response response = given().spec(request)
                .pathParam("vote_id", voteId)
                .when()
                .delete("v1//{vote_id}");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("SUCCESS", response.jsonPath().get("message"));

    }

    @Test
    public void GetCategoryById() {

            given()
                    .spec(request)
                    .queryParam("id", "1")
                    .when()
                    .get("v1/categories")
                    .then()
                    .spec(success);
            System.out.println("");


    }
}


