package com.amertkara.campsitemanager.controller;

import static com.amertkara.campsitemanager.controller.CampsiteController.CAMPSITE_PATH;
import static com.amertkara.campsitemanager.controller.ReservationController.RESERVATIONS_PATH;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.amertkara.campsitemanager.controller.dto.ReservationDTO;
import com.amertkara.campsitemanager.controller.dto.fixture.ReservationDTOFixture;
import com.amertkara.campsitemanager.model.repository.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@SpringBootTest(webEnvironment = MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration
@WebAppConfiguration
public class CampsiteControllerIT extends AbstractTestNGSpringContextTests {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	private static final String IS_AVAILABLE_JSON_PATH = "$.available";
	private static final String IS_AVAILABLE_DAYS_JSON_PATH = "$.availableDays";
	private static final String UNAVAILABLE_DATES_JSON_PATH = "$.unavailableDates";

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ReservationRepository reservationRepository;
	private ObjectMapper objectMapper = new ObjectMapper();

	@AfterMethod
	public void afterMethod() {
		reservationRepository.deleteAll();
	}

	@Test
	public void testGet_givenOneMonthRange_shouldGetAvailability() throws Exception {
		// First reservation is for 3 days
		ReservationDTO reservation1 = ReservationDTOFixture.basic();
		reservation1.setArrivalDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 2)));
		reservation1.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 5)));
		// Second reservation is for 2 days
		ReservationDTO reservation2 = ReservationDTOFixture.basic();
		reservation2.setArrivalDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 8)));
		reservation2.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 10)));
		// Third reservation is for 2 days
		ReservationDTO reservation3 = ReservationDTOFixture.basic();
		reservation3.setArrivalDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 10)));
		reservation3.setDepartureDate(DATE_FORMATTER.format(DateUtils.addDays(Date.from(Instant.now()), 12)));

		// Given 3 existing reservations
		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservation1))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservation2))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION));

		mockMvc.perform(put(RESERVATIONS_PATH).content(objectMapper.writeValueAsString(reservation3))
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(CREATED.value()))
				.andExpect(header().exists(LOCATION));

		mockMvc.perform(get(CAMPSITE_PATH)
				.contentType(APPLICATION_JSON))
				.andExpect(status().is(OK.value()))
				.andExpect(jsonPath(IS_AVAILABLE_JSON_PATH, is(true)))
				.andExpect(jsonPath(IS_AVAILABLE_DAYS_JSON_PATH, is(30 - 7)))
				.andExpect(jsonPath(UNAVAILABLE_DATES_JSON_PATH, hasSize(3)));
	}
}