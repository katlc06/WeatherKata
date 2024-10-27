package weatherdata;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;


class WeatherDataTest {

	@Test
    public void testGetMockWeatherData_ValidCity() {
        JSONObject result = WeatherData.getMockWeatherData("London");
        assertNotNull(result);
        assertEquals("London", result.getString("name"));
        assertEquals(280.32, result.getJSONObject("main").getDouble("temp"));
    }

    @Test
    public void testGetMockWeatherData_InvalidCity() {
        JSONObject result = WeatherData.getMockWeatherData("InvalidCityName");
        assertNull(result);
    }

}
