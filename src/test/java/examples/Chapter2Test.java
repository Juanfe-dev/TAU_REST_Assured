package examples;

import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Chapter2Test {

    @Test //Waiting for 200 status code
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() {

        given().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            assertThat().
            statusCode(200);
    }

    @Test //Waiting for .JSON file type
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {
        given().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            assertThat().
            contentType(ContentType.JSON);
            //contentType("application/json"); //String Literal Representation
            //contentType(ContentType.XML); //Expectation Failed
    }

     @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails() {
        given().
            log().all().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            log().body();
    }

     @Test
     public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {
         given().
                 when().
                    get("http://zippopotam.us/us/90210").
                 then().
                 assertThat().
                    body("places[0].'place name'", //JSON Path expression with exact location
                    equalTo("Beverly Hills")); //Expectation value
     }

    @Test
    public void requestUsZipCode90210_checkListOfPlaceNamesInResponseBody_expectContainsBeverlyHills()
    {
        given().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            assertThat().
            body("places.'place name'", hasItem("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkNumberOfPlaceNamesInResponseBody_expectOne() {
        given().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            assertThat().
            body("places.'place name'", hasSize(1));
    }
}