import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WeatherAPI {
    private static final String API_KEY = "your_api_key_here";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    public String getWeather(String city) throws Exception {
        URL url = new URL(String.format(API_URL, city, API_KEY));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            content.append("\n");
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }
}
