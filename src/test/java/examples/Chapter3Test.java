package examples;

import com.tngtech.java.junit.dataprovider.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(DataProviderRunner.class)
public class Chapter3Test {

    @DataProvider //Step 1: Create a test data collection
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                // Run the test 3 times, one by one
                { "us", "90210", "Beverly Hills" }, // First time
                { "us", "12345", "Schenectady" }, //Second time
                { "ca", "B2R", "Waverley"}, //Third time
                {"nl", "1001", "Amsterdam"} //Update the specified test data
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces") //Step 2: Feed data collection to your test method
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName)
    {
        given().
            pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
        when().
            get("http://zippopotam.us/{countryCode}/{zipCode}"). //Replace the URL hard coded parameters
        then().
            assertThat().
            body("places[0].'place name'", equalTo(expectedPlaceName)); //Replace the hard coded "Beverly Hills" parameters
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
}