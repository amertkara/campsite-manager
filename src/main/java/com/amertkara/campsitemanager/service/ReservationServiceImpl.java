package com.amertkara.campsitemanager.service;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.exception.ErrorPayload;
import com.amertkara.campsitemanager.exception.ReservationDoesNotExistException;
import com.amertkara.campsitemanager.model.Reservation;
import com.amertkara.campsitemanager.model.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService {
	private final ReservationRepository reservationRepository;
	private final MapperFactory mapperFactory;

	@Override
	@Transactional
	public String saveOrUpdate(ReservationDTO reservationDTO) {
		return reservationRepository.save(mapperFactory.getMapperFacade().map(reservationDTO, Reservation.class)).getUuid();
	}

	@Override
	@Transactional
	public ReservationDTO get(String reservationUuid) {
		return mapperFactory.getMapperFacade().map(reservationRepository.getByUuid(reservationUuid).orElseThrow(() -> new ReservationDoesNotExistException(ErrorPayload.buildReservationNotFoundPayload(reservationUuid))), ReservationDTO.class);
	}

	@Override
	@Transactional
	public void delete(String reservationUuid) {
		reservationRepository.deleteByUuid(reservationUuid);
	}
}
