package com.dmdev.http.service;

import com.dmdev.http.dao.FlightDao;
import com.dmdev.http.dto.FlightDto;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

// singleton
@NoArgsConstructor(access = PRIVATE)
public class FlightService
{
    private static final FlightService INSTANCE = new FlightService();
    // добавляем данное поле, чтобы видеть зависимости FlightService от FlightDao
    private final FlightDao flightDao = FlightDao.getInstance();

    public List<FlightDto> findAll()
    {
        // преобразуем entity в dto
        return flightDao.findAll().stream()
            .map(flight -> FlightDto.builder()
                            .id(flight.getId())
                            .description(
                                """
                                    %s - %s - %s
                                """.formatted(
                                    flight.getDepartureAirportCode(),
                                    flight.getArrivalAirportCode(),
                                    flight.getStatus()))
                .build())
            .collect(toList());
    }

    public static FlightService getInstance()
    {
        return INSTANCE;
    }
}
