package simpleMeteo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import simpleMeteo.model.WeatherData;

@Service
public class OpenWeatherService {

    @Value("${api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    private final RestTemplate restTemplate;

    @Autowired
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Метод создает URL запрос и получает данные от OpenWeatherAPI
    public WeatherData getWeatherData(String city) {
        String url = API_URL + city + "&appid=" + apiKey + "&units=metric";

        String jsonResponse = restTemplate.getForObject(url, String.class);

        return parseWeatherData(jsonResponse);
    }

    // Преобразует данные  из json в String для вывода пользователю
    private WeatherData parseWeatherData(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        WeatherData weatherData = new WeatherData();

        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject wind = jsonObject.getJSONObject("wind");
        JSONArray weatherArray = jsonObject.getJSONArray("weather");

        // Получение имя города
        weatherData.setCityName(jsonObject.getString("name"));
        // Получение температуры города
        weatherData.setTemperature(String.format("%.1f", main.getDouble("temp" ))+ "°C");
        // Получение скорости ветра
        weatherData.setHumidity(main.getDouble("humidity"));
        // Получение скорости ветера
        weatherData.setWindSpeed(wind.getDouble("speed"));
        // Получение описание погоды
        if (weatherArray.length() > 0) {
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            weatherData.setWeatherDescription(weatherObj.getString("main"));
        }

        return weatherData;
    }
}
