package com.amertkara.campsitemanager.model.repository;

import com.amertkara.campsitemanager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> getByUuid(String uuid);

	void deleteByUuid(String uuid);

	@Query(value = "select r from Reservation r where " +
			"(arrival_date > :arrivalDate and arrival_date < :departureDate) or " +
			"(departure_date > :arrivalDate and departure_date < :departureDate) or " +
			"(arrival_date = :arrivalDate and departure_date = :departureDate)")
	List<Reservation> getOverlappingReservations(@Param("arrivalDate") Date arrivalDate, @Param("departureDate") Date departureDate);
}
