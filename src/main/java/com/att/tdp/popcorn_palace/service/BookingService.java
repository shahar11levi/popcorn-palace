package com.att.tdp.popcorn_palace.service;

import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeService showtimeService;

    public BookingService(BookingRepository bookingRepository, ShowtimeService showtimeService) {
        this.bookingRepository = bookingRepository;
        this.showtimeService = showtimeService;
    }

    public Booking addBooking(Booking booking) {
        Showtime showtime  = booking.getShowtime();
        if (showtime == null || showtimeService.getShowtimeById(showtime.getId()) == null)
            throw new RuntimeException("The showtime " + showtime.getId() + " not found");
        if (!checkSeatAvailability(showtime, booking.getSeatNumber()))
            throw new RuntimeException("The seat " + booking.getSeatNumber() + " is not available");
        return bookingRepository.save(booking);
    }

    private boolean checkSeatAvailability(Showtime showtime, int seat) {
        return true;
    }


}



