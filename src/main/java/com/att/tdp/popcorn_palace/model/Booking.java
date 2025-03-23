package com.att.tdp.popcorn_palace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;


@Entity
public class Booking {
    @Id 
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;
    private int seatNumber;
    private UUID userId;

    Booking() {}

    public Booking(Showtime showtime, int seatNumber, UUID userId){
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    

}
