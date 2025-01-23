package com.tekarch.BookingMS.Services.Impl;


import com.tekarch.BookingMS.DTO.BookingDTO;
import com.tekarch.BookingMS.Services.BookingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final RestTemplate restTemplate;
    private final String datastoreServiceUrl;

    public BookingServiceImpl(RestTemplate restTemplate, @Value("${datastore.service.url}") String datastoreServiceUrl) {
        this.restTemplate = restTemplate;
        this.datastoreServiceUrl = datastoreServiceUrl;
    }

    @Override
    public BookingDTO createBooking(BookingDTO booking) {
        // Correctly call the availableSeats endpoint in TafDatastoreService
        String flightUrl = datastoreServiceUrl + "/flights/" + booking.getFlightId();  // Ensure the correct URL path
        Integer availableSeats = restTemplate.getForObject(flightUrl + "/availableSeats", Integer.class);

        if (availableSeats == null || availableSeats <= 0) {
            throw new RuntimeException("Flight is fully booked or not available.");
        }

        // Reduce the available seats by calling the endpoint in TafDatastoreService (ensure it's only called once)
        restTemplate.put(flightUrl + "/reduceSeats", null);

        // Create the booking in TafDatastoreService
        String bookingUrl = datastoreServiceUrl + "/bookings";
        return restTemplate.postForObject(bookingUrl, booking, BookingDTO.class);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        String bookingUrl = datastoreServiceUrl + "/bookings";
        BookingDTO[] bookingsArray = restTemplate.getForObject(bookingUrl, BookingDTO[].class);
        return Arrays.asList(bookingsArray);
    }

    @Override
    public BookingDTO getBookingById(Long bookingId) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId;
        return restTemplate.getForObject(bookingUrl, BookingDTO.class);
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, BookingDTO updatedBooking) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId;
        restTemplate.put(bookingUrl, updatedBooking);
        return updatedBooking;
    }

    @Override
    public void deleteBooking(Long bookingId) {
        String bookingUrl = datastoreServiceUrl + "/bookings/" + bookingId + "/cancel";
        restTemplate.put(bookingUrl, null);
    }
}
