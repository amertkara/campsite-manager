package com.amertkara.campsitemanager.service;

import static com.amertkara.campsitemanager.exception.ErrorPayload.buildOverlappingDatesPayload;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.exception.ErrorPayload;
import com.amertkara.campsitemanager.exception.OveralappingDatesException;
import com.amertkara.campsitemanager.exception.ReservationDoesNotExistException;
import com.amertkara.campsitemanager.model.Reservation;
import com.amertkara.campsitemanager.model.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationServiceImpl implements ReservationService {
	private final ReservationRepository reservationRepository;
	private final MapperFactory mapperFactory;

	@Override
	@Transactional
	public String saveOrUpdate(ReservationDTO reservationDTO) {
		Reservation reservation = mapperFactory.getMapperFacade().map(reservationDTO, Reservation.class);
		List<ReservationDTO> overlappingReservations = mapperFactory.getMapperFacade().mapAsList(reservationRepository.getOverlappingReservations(reservation.getArrivalDate(), reservation.getDepartureDate()), ReservationDTO.class);
		if (!overlappingReservations.isEmpty()) {
			throw new OveralappingDatesException(buildOverlappingDatesPayload(overlappingReservations));
		}
		return reservationRepository.save(reservation).getUuid();
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

	@Override
	public long count() {
		return reservationRepository.count();
	}
}