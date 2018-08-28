package com.amertkara.campsitemanager.model.repository;

import com.amertkara.campsitemanager.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> getByUuid(String uuid);

	void deleteByUuid(String uuid);

	boolean existsByEmail(String email);
}
