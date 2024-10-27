package weatherdata;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

/**
 * Tests the WeatherData Class
 * @author Kathryn Cheng
 */
class WeatherDataTest {
	/**
	 * Tests the WeatherData class with a valid city.
	 */
	@Test
    public void testGetMockWeatherData_ValidCity() {
        JSONObject result = WeatherData.getMockWeatherData("London");
        assertNotNull(result);
        assertEquals("London", result.getString("name"));
        assertEquals(280.32, result.getJSONObject("main").getDouble("temp"));
    }
	/**
	 * Tests the WeatherData class with an invalid city.
	 */
    @Test
    public void testGetMockWeatherData_InvalidCity() {
        JSONObject result = WeatherData.getMockWeatherData("InvalidCityName");
        assertNull(result);
    }

}
