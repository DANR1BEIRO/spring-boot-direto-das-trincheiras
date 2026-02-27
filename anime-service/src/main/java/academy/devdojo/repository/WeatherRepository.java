package academy.devdojo.repository;

import external.dependency.ExternalWeatherApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WeatherRepository {
    public final ExternalWeatherApi weatherApi;

    public String getCityWeather(String city) {
        return weatherApi.fetchTemperature(city);
    }
}


