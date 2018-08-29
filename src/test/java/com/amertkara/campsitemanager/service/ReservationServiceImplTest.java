package com.amertkara.campsitemanager.service;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.amertkara.campsitemanager.exception.OverlappingDatesException;
import com.amertkara.campsitemanager.exception.ReservationDoesNotExistException;
import com.amertkara.campsitemanager.model.Reservation;
import com.amertkara.campsitemanager.model.repository.ReservationRepository;
import com.google.common.collect.ImmutableList;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReservationServiceImplTest {
	@Mock
	private ReservationRepository reservationRepository;
	@Mock
	private MapperFactory mapperFactory;
	@Mock
	private MapperFacade mapperFacade;
	private ReservationService reservationService;
	private ReservationDTO reservationDTO;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		reservationService = new ReservationServiceImpl(reservationRepository, mapperFactory);
		reservationDTO = ReservationDTOFixture.complete();

		when(mapperFactory.getMapperFacade()).thenReturn(mapperFacade);
	}

	@Test
	public void testSaveOrUpdate_givenOverlappingReservationsExists_shouldThrowExceptionAndNeverCallSave() {
		List<ReservationDTO> overlappingReservations = ImmutableList.of(ReservationDTOFixture.complete(), ReservationDTOFixture.complete());
		Reservation reservation = new Reservation();
		when(mapperFacade.map(eq(reservationDTO), eq(Reservation.class))).thenReturn(reservation);
		when(mapperFacade.mapAsList(anyList(), eq(ReservationDTO.class))).thenReturn(overlappingReservations);

		assertThatExceptionOfType(OverlappingDatesException.class)
				.isThrownBy(() -> reservationService.saveOrUpdate(reservationDTO));

		verify(reservationRepository, never()).save(any());
		verify(reservationRepository).getOverlappingReservations(any(), any());
	}

	@Test
	public void testSaveOrUpdate_givenUpdateFlow_shouldNotCheckForOverlappingReservations() {
		reservationDTO.setId(nextLong()); // ID is set, it means we are updating a reservation
		Reservation reservation = new Reservation();
		when(mapperFacade.map(eq(reservationDTO), eq(Reservation.class))).thenReturn(reservation);
		when(reservationRepository.save(eq(reservation))).thenReturn(reservation);

		reservationService.saveOrUpdate(reservationDTO);

		verify(reservationRepository).save(any());
		verify(reservationRepository, never()).getOverlappingReservations(any(), any());
	}

	@Test
	public void testSaveOrUpdate_givenNoOverlappingReservationsExists_shouldCallSave() {
		Reservation reservation = new Reservation();
		when(mapperFacade.map(eq(reservationDTO), eq(Reservation.class))).thenReturn(reservation);
		when(mapperFacade.mapAsList(anyList(), eq(ReservationDTO.class))).thenReturn(emptyList());
		when(reservationRepository.save(eq(reservation))).thenReturn(reservation);

		reservationService.saveOrUpdate(reservationDTO);

		verify(reservationRepository).save(eq(reservation));
		verify(reservationRepository).getOverlappingReservations(any(), any());
	}

	@Test
	public void testGet_givenReservationDoesNotExist_shouldThrowException() {
		when(reservationRepository.getByUuid(anyString())).thenThrow(ReservationDoesNotExistException.class);

		assertThatExceptionOfType(ReservationDoesNotExistException.class)
				.isThrownBy(() -> reservationService.get(randomUUID().toString()));
		verify(reservationRepository).getByUuid(anyString());
	}

	@Test
	public void testGet_givenExistingReservation_shouldReturnReservation() {
		Reservation reservation = new Reservation();
		ReservationDTO reservationDTO = ReservationDTOFixture.complete();
		when(mapperFacade.map(eq(reservation), eq(ReservationDTO.class))).thenReturn(reservationDTO);
		when(reservationRepository.getByUuid(anyString())).thenReturn(Optional.of(reservation));

		assertThat(reservationService.get(randomUUID().toString())).isEqualTo(reservationDTO);
		verify(reservationRepository).getByUuid(anyString());
	}

	@Test
	public void testDelete_givenReservationUuid_shouldCallDelete() {
		reservationService.delete(randomUUID().toString());

		verify(reservationRepository).deleteByUuid(anyString());
	}

	@Test
	public void testGetReservations_givenDateRange_shouldGetReservations() {
		Date startDate = Date.from(Instant.now());
		Date endDate = Date.from(Instant.now());
		List<ReservationDTO> reservations = ImmutableList.of(ReservationDTOFixture.complete(), ReservationDTOFixture.complete());
		when(mapperFacade.mapAsList(anyList(), eq(ReservationDTO.class))).thenReturn(reservations);

		assertThat(reservationService.getReservations(startDate, endDate)).hasSameElementsAs(reservations);
		verify(reservationRepository).getOverlappingReservations(eq(startDate), eq(endDate));
	}
}