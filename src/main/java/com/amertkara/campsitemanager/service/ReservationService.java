package com.amertkara.campsitemanager.service;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;

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
	 * Returns the total number of reservations for the campsite.
	 *
	 * @return
	 */
	long count();
}
