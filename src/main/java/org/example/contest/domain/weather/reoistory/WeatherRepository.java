package org.example.contest.domain.weather.reoistory;

import org.example.contest.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

}
