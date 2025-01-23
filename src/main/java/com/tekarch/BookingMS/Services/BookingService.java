package com.tekarch.BookingMS.Services;

import com.tekarch.BookingMS.DTO.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookings);
    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Long bookingId);
    BookingDTO updateBooking(Long bookingId,BookingDTO updatedBookings);
    void deleteBooking(Long bookingId);
}
