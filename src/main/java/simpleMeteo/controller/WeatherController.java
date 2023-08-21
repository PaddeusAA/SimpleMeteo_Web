package simpleMeteo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import simpleMeteo.model.WeatherData;
import simpleMeteo.service.OpenWeatherService;

@Controller
public class WeatherController {

    private final OpenWeatherService openWeatherService;

    @Autowired
    public WeatherController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    @RequestMapping("/")
    public String showWeatherForm() {
        return "homepage";
    }

    @PostMapping("/get-weather")
    public String getWeather(@RequestParam String city, Model model){
        WeatherData weatherData = openWeatherService.getWeatherData(city);
        model.addAttribute("weatherData", weatherData);
        return "homepage";
    }
}
