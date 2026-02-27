package academy.devdojo.controller;

import academy.devdojo.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherRepository repository;

    @GetMapping
    public String checkWeather(@RequestParam(required = true) String city) {
        return repository.getCityWeather(city);
    }
}
