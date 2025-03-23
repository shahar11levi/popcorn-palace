package com.att.tdp.popcorn_palace.service;

import org.springframework.stereotype.Service;

import com.att.tdp.popcorn_palace.model.Booking;
import com.att.tdp.popcorn_palace.model.Showtime;
import com.att.tdp.popcorn_palace.repository.BookingRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;
    private final ShowtimeService showtimeService;

    public BookingService(BookingRepository bookingRepository, ShowtimeRepository showtimeRepository, ShowtimeService showtimeService) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
        this.showtimeService = showtimeService;
    }

    @Transactional
    public Booking addBooking(Booking booking) {
        Showtime showtime  = booking.getShowtime();
        if (showtime == null || showtimeService.getShowtimeById(showtime.getId()) == null)
            throw new RuntimeException("The showtime " + showtime.getId() + " not found");
        if (!checkSeatAvailability(showtime, booking.getSeatNumber()))
            throw new RuntimeException("The seat " + booking.getSeatNumber() + " is not available");
        return bookingRepository.save(booking);
    }

    private boolean checkSeatAvailability(Showtime showtime, int seat) {
        showtime = showtimeRepository.findById(showtime.getId()).get();
        if (showtime.getBookings() == null)
            return true;
        for (Booking booking : showtime.getBookings()) {
            if (booking.getSeatNumber() == seat)
                return false;
        }
        return true;
    }


}



