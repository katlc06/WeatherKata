package weatherdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Retrieves data from a current weather data API and prints the data in a user friendly way
 * @author Kathryn Cheng
 */
public class WeatherData {
	// Get API when logged into OpenWeatherMap
	// API Key: A unique string used to authenticate your requests to the OpenWeatherMap API
	
	/** Holds user's unique key for accessing the OpenWeatherMap API to help with API requests */
    private static final String API_KEY = "0b2a285cc5ec2c8c978e9e93f74db59e";
    
    /**
     * Gathers information regarding a city inputted by the user and prints it out in a user friendly way.
     */
    public static void main() {
    	// Attempts to read in and print the weather the user has inputted
        try {
            // Prompts the user for the city name
            System.out.print("Enter city name: ");
            // Reader helps read in the city the user inputted
            // (BufferedReader allows dynamic output from the user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Reads the user input
            String cityName = reader.readLine();
            
            // Attempts to get the city's weather data from the API
            JSONObject weatherData = fetchWeather(cityName);
            // If the data retrieved, displays the city's weather data
            if (weatherData != null) {
                displayWeather(weatherData);
            }
        } catch (Exception e) {
        	// Prints exception if one exists while running this program
            e.printStackTrace();
        }
    }
    /**
     * Constructs the URL for the API request using the city name and API key. 
     * @param cityName the city name the user wants to see the weather data for
     * @return a JSON object of the weather data
     */
    private static JSONObject fetchWeather(String cityName) {
    	// The units=metric parameter specifies that the temperature should be returned in Celsius
    	// Gets the URL string to the given city's data
        String urlString = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", cityName, API_KEY);
        try {
        	// Creates a URL object
            URL url = new URL(urlString);
            // Opens an HttpURLConnection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Sends a GET request to the API
            conn.setRequestMethod("GET");
            // Establishes the connection to the API
            conn.connect();
            
            // Since the URL is an https, checks if the response code is 200 (HTTP OK)
            if (conn.getResponseCode() != 200) {
            	// If not 200, prints an error and returns null, showing that the data
            	// could not be retrieved
                System.out.println("Error: " + conn.getResponseCode());
                return null;
            }

            // Reads from the API data input
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            // Sets up a json response which helps capture all the data from the API
            StringBuilder jsonResponse = new StringBuilder();
            // Keeps track of what string we are on
            String line;
            // Reads the json response from the API line by line
            while ((line = reader.readLine()) != null) {
            	// Appends it to a StringBuilder object to build a JSON response
                jsonResponse.append(line);
            }
            reader.close();

            // Converts JSON response to JSONObject (makes it easier to access specific data fields)
            return new JSONObject(jsonResponse.toString());

        } catch (Exception e) {
        	// Prints exception if one exists while running this program
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Retrieves weather data details from a JSON object and prints them in a user friendly way.
     * @param data the JSON object that contains the weather data details for given cities
     */
    private static void displayWeather(JSONObject data) {
    	// Retrieves weather details from the JSON object and stores the data into strings, doubles, and ints
        String city = data.getString("name");
        String country = data.getJSONObject("sys").getString("country");
        double temperature = data.getJSONObject("main").getDouble("temp");
        double feelsLike = data.getJSONObject("main").getDouble("feels_like");
        int humidity = data.getJSONObject("main").getInt("humidity");
        int pressure = data.getJSONObject("main").getInt("pressure");
        double windSpeed = data.getJSONObject("wind").getDouble("speed");
        String weatherDescription = data.getJSONArray("weather").getJSONObject(0).getString("description");
        double tempMin = data.getJSONObject("main").getDouble("temp_min");
        double tempMax = data.getJSONObject("main").getDouble("temp_max");
        String icon = data.getJSONArray("weather").getJSONObject(0).getString("icon");
        
        // Prints out the weather details stored in a user friendly list format
        System.out.println("\nWeather Information:");
        System.out.printf("City: %s, %s%n", city, country);
        System.out.printf("Temperature: %.2f °C%n", temperature);
        System.out.printf("Feels Like: %.2f °C%n", feelsLike);
        System.out.printf("Min Temperature: %.2f °C%n", tempMin);
        System.out.printf("Max Temperature: %.2f °C%n", tempMax);
        System.out.printf("Humidity: %d%%%n", humidity);
        System.out.printf("Pressure: %d hPa%n", pressure);
        System.out.printf("Wind Speed: %.2f m/s%n", windSpeed);
        System.out.printf("Weather: %s%n", weatherDescription);
        System.out.println("Weather Icon: https://openweathermap.org/img/wn/" + icon + "@2x.png");
    }
    
 // New method for testing
    /**
     * Method just to help test the other methods.
     * @param cityName the mock city name to get the data from
     * @return the mock weather data for the city
     */
    public static JSONObject getMockWeatherData(String cityName) {
        if (cityName.equals("London")) {
            // Creates a JSONObject that mimics an  API response
            JSONObject mockData = new JSONObject();
            
            mockData.put("name", "London");
            
            // Puts aall the objects into the JSON object to help set up the mock data
            JSONObject main = new JSONObject();
            main.put("temp", 280.32);
            main.put("feels_like", 278.99);
            main.put("humidity", 60); 
            main.put("pressure", 1012); 
            main.put("temp_min", 279.15); 
            main.put("temp_max", 281.15);
            
            mockData.put("main", main);
            
            // Adds the wind data
            JSONObject wind = new JSONObject();
            wind.put("speed", 4.1); // Mock wind speed
            mockData.put("wind", wind);
            
            // Adds the weather description
            mockData.put("weather", new JSONArray().put(new JSONObject().put("description", "light rain")));
            
            // Returns the mock data of the city
            return mockData;
        }
        // Does not get data for other cities
        return null;
    }
}
