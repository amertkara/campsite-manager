package com.amertkara.campsitemanager.service;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;

import java.util.Date;
import java.util.List;

public interface ReservationService {
	/**
	 * Creates a reservation in the database for a given payload.
	 *
	 * @param reservationDTO
	 * @return UUID of the newly created object
	 */
	String saveOrUpdate(ReservationDTO reservationDTO);

	/**
	 * Gets a reservation for a given uuid.
	 *
	 * @param reservationUuid
	 * @return
	 */
	ReservationDTO get(String reservationUuid);

	/**
	 * Deletes a reservation for a given uuid.
	 *
	 * @param reservationUuid
	 */
	void delete(String reservationUuid);

	/**
	 * Returns the reservations for a date range.
	 *
	 * @param startDate beginning of the range
	 * @param endDate end of the range
	 * @return
	 */
	List<ReservationDTO> getReservations(Date startDate, Date endDate);
}
