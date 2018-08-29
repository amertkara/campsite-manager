package com.amertkara.campsitemanager.controller;

import static com.amertkara.campsitemanager.config.ServiceConstants.DATE_FORMAT;
import static com.amertkara.campsitemanager.controller.ReservationController.RESERVATIONS_PATH;
import static com.amertkara.campsitemanager.controller.dto.util.DateParser.toLocalDate;
import static com.amertkara.campsitemanager.exception.ErrorCode.*;
import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationControllerIT extends AbstractTestNGSpringContextTests {
	private static final String ERROR_CODE_JSON_PATH = "$.errorCode";
	private static final String FULL_NAME_JSON_PATH = "$.fullName";
	private static final String EMAIL_JSON_PATH = "$.email";
	private static final String ARRIVAL_DATE_JSON_PATH = "$.arrivalDate";
	private static final String DEPARTURE_DATE_JSON_PATH = "$.departureDate";
	private static final String ID_JSON_PATH = "$.id";
	private static final String DURATION_JSON_PATH = "$.duration";
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testCreate_givenPayloadMissingFullName_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setFullName("");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(PAYLOAD_NOT_VALID.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenPayloadMissingEmail_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setEmail("");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(PAYLOAD_NOT_VALID.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenPayloadMissingArrivalDate_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setArrivalDate("");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(PAYLOAD_NOT_VALID.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenPayloadMissingDepartureDate_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setDepartureDate("");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(PAYLOAD_NOT_VALID.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenBadFormattedArrivalDate_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setArrivalDate("2018-23-12");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(INVALID_DATE_FORMAT.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenBadFormattedDepartureDate_shouldReturnBadRequest() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setDepartureDate("2018-23-12");

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(INVALID_DATE_FORMAT.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenTooSoonArrivalDate_shouldReturnBadRequest() throws Exception {
		Date newArrivalDate = DateUtils.addDays(Date.from(Instant.now()), 1);
		Date newDepartureDate = DateUtils.addDays(newArrivalDate, 2);
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setArrivalDate(DATE_FORMATTER.format(newArrivalDate));
		reservationDTO.setDepartureDate(DATE_FORMATTER.format(newDepartureDate));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(INVALID_ARRIVAL_DATE.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenTooFarArrivalDate_shouldReturnBadRequest() throws Exception {
		Date newArrivalDate = DateUtils.addDays(Date.from(Instant.now()), 33);
		Date newDepartureDate = DateUtils.addDays(newArrivalDate, 2);
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setArrivalDate(DATE_FORMATTER.format(newArrivalDate));
		reservationDTO.setDepartureDate(DATE_FORMATTER.format(newDepartureDate));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(INVALID_ARRIVAL_DATE.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test
	public void testCreate_givenPayloadWithExceedingDuration_shouldReturnBadRequest() throws Exception {
		Date newArrivalDate = DateUtils.addDays(Date.from(Instant.now()), 2);
		Date newDepartureDate = DateUtils.addDays(newArrivalDate, 10); // duration set to 10 days
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		reservationDTO.setArrivalDate(DATE_FORMATTER.format(newArrivalDate));
		reservationDTO.setDepartureDate(DATE_FORMATTER.format(newDepartureDate));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(INVALID_RESERVATION_DURATION.name())))
				.andExpect(status().is(BAD_REQUEST.value()));
	}

	@Test(description = "happy path")
	public void testCreate_givenValid_shouldPersistAndReturnLocationHeader() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION));
	}

	@Test(description = "Reservation conflict")
	public void testCreate_givenTwoUserHaveOverlappingDates_shouldReturnConflict() throws Exception {
		ReservationDTO reservationDTOSuccessful = ReservationDTOFixture.basic();
		ReservationDTO reservationDTOFailed = ReservationDTOFixture.basic();
		reservationDTOFailed.setArrivalDate(DATE_FORMATTER.format(DateUtils.addDays(DATE_FORMATTER.parse(reservationDTOSuccessful.getArrivalDate()), 1)));
		reservationDTOFailed.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(DATE_FORMATTER.parse(reservationDTOSuccessful.getDepartureDate()), 1)));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTOSuccessful))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTOFailed))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(OVERLAPPING_DATES.name())))
				.andExpect(status().is(CONFLICT.value()));
	}

	@Test
	public void testGet_givenNonExistingReservation_shouldReturnNotFound() throws Exception {
		mockMvc.perform(get(RESERVATIONS_PATH + "/" + randomUUID().toString()))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(RESERVATION_NOT_FOUND.name())))
				.andExpect(status().is(NOT_FOUND.value()));
	}

	@Test
	public void testGet_givenExistingReservation_shouldReturn() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();
		LocalDate arrivalDate = toLocalDate(reservationDTO.getArrivalDate());
		LocalDate departureDate = toLocalDate(reservationDTO.getDepartureDate());
		Long differenceInDays = Duration.between(arrivalDate.atStartOfDay(), departureDate.atStartOfDay()).toDays();

		MvcResult mvcResult = mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION))
				.andReturn();

		mockMvc.perform(get(mvcResult.getResponse().getHeader(LOCATION)))
				.andExpect(status().is(OK.value()))
				.andExpect(jsonPath(FULL_NAME_JSON_PATH, is(reservationDTO.getFullName())))
				.andExpect(jsonPath(EMAIL_JSON_PATH, is(reservationDTO.getEmail())))
				.andExpect(jsonPath(ARRIVAL_DATE_JSON_PATH, is(reservationDTO.getArrivalDate())))
				.andExpect(jsonPath(DEPARTURE_DATE_JSON_PATH, is(reservationDTO.getDepartureDate())))
				.andExpect(jsonPath(DURATION_JSON_PATH, is(differenceInDays.intValue())))
				.andExpect(jsonPath(ID_JSON_PATH, notNullValue()))
				.andReturn();
	}

	@Test
	public void testUpdate_givenNonExistingReservation_shouldReturnNotFound() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();

		mockMvc.perform(post(RESERVATIONS_PATH + "/" + randomUUID().toString()).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(RESERVATION_NOT_FOUND.name())))
				.andExpect(status().is(NOT_FOUND.value()));
	}

	@Test
	public void testUpdate_givenExistingReservation_shouldUpdate() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();

		MvcResult mvcResult = mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION))
				.andReturn();

		String newFullName = "Alex Ferguson";
		reservationDTO.setFullName(newFullName);
		mockMvc.perform(post(mvcResult.getResponse().getHeader(LOCATION)).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(NO_CONTENT.value()));

		mockMvc.perform(get(mvcResult.getResponse().getHeader(LOCATION)))
				.andExpect(status().is(OK.value()))
				.andExpect(jsonPath(FULL_NAME_JSON_PATH, is(newFullName)));
	}

	@Test
	public void testDelete_givenReservation_shouldDelete() throws Exception {
		ReservationDTO reservationDTO = ReservationDTOFixture.basic();

		MvcResult mvcResult = mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservationDTO))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION))
				.andReturn();

		mockMvc.perform(delete(mvcResult.getResponse().getHeader(LOCATION)))
				.andExpect(status().is(NO_CONTENT.value()));

		mockMvc.perform(get(mvcResult.getResponse().getHeader(LOCATION)))
				.andExpect(jsonPath(ERROR_CODE_JSON_PATH, is(RESERVATION_NOT_FOUND.name())))
				.andExpect(status().is(NOT_FOUND.value()));
	}
}