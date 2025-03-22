package com.att.tdp.popcorn_palace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.att.tdp.popcorn_palace.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}