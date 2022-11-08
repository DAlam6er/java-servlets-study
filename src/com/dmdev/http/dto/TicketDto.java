package com.dmdev.http.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TicketDto
{
    // boilerplate code replaced by Lombok annotations
    /*
    private final Long id;
    private final Long flightId;
    private final String seatNo;

    public TicketDto(Long id, Long flightId, String seatNo)
    {
        this.id = id;
        this.flightId = flightId;
        this.seatNo = seatNo;
    }

    public Long getId()
    {
        return id;
    }

    public Long getFlightId()
    {
        return flightId;
    }

    public String getSeatNo()
    {
        return seatNo;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketDto ticketDto = (TicketDto) o;
        return id.equals(ticketDto.id) &&
            flightId.equals(ticketDto.flightId) &&
            seatNo.equals(ticketDto.seatNo);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, flightId, seatNo);
    }

    @Override
    public String toString()
    {
        return "TicketDto{" +
            "id=" + id +
            ", flightId=" + flightId +
            ", seatNo='" + seatNo + '\'' +
            '}';
    }
     */
    String seatNo;
    Long id;
    Long flightId;
}
