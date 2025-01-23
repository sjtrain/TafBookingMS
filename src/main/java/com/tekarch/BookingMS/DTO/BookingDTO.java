package com.tekarch.BookingMS.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDTO {

    private Long bookingId;
    private Long userId;
    private Long flightId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
