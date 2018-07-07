package com.crossover.techtrial.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.service.HourlyElectricityService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * 
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {

	MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	@Mock
	private PanelController panelController;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	HourlyElectricityService hourlyElectricityService;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
	}

	@Test
	public void testPanelNotRpeated() throws Exception {
		HttpEntity<Object> panel = getHttpEntity(
				"{\"serial\": \"1234567890123456\", \"longitude\": \"54.123232\", \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
		ResponseEntity<Panel> response = template.postForEntity("/api/register", panel, Panel.class);
		Assert.assertEquals(400, response.getStatusCode().value());
	}

	@Test
	public void testallDailyElectricityFromYesterday() throws Exception {

		List<DailyElectricity> dailyMockElectricityList = hourlyElectricityService
				.ElectricityByPanelSerialAndDate(1234567890123456L);
		ResponseEntity<List<DailyElectricity>> response = template.exchange("/api/panels/1234567890123456/daily",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<DailyElectricity>>() {
				});
		Assert.assertEquals(200, response.getStatusCode().value());
		assertThat(dailyMockElectricityList, is(response.getBody()));
	}

	@Test
	public void testSaveHourElectricity() {

		HttpEntity<Object> hourlyElectricity = getHttpEntity(
				"{\"panel_id\": \"1\", \"generated_electricity\": \"100\", \"reading_at\": \"2018-03-25\"}");
		ResponseEntity<String> response = template.postForEntity("/api/panels/1234567890123456/hourly",
				hourlyElectricity, String.class);
		Assert.assertEquals(202, response.getStatusCode().value());

	}

	@Test
	public void testSavePanel() {

		long randomSerial = new Random().longs(1000000000000000L, 9999999999999999L).findFirst().getAsLong();

		HttpEntity<Object> panel = getHttpEntity(
				"{\"serial\" : \"randomSerial\",\"latitude\" : 16.999671,\"longitude\" : 51.104394,\"brand\" : \"tesla\" }"
						.replace("randomSerial", "" + randomSerial));
		ResponseEntity<String> response = template.postForEntity("/api/register", panel, String.class);
		Assert.assertEquals(202, response.getStatusCode().value());

	}
	
	@Test
	public void testNotPanelFound() {

		ResponseEntity<List<HourlyElectricity>> response = template.exchange("/api/panels/234234366545645/hourly",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<HourlyElectricity>>() {
				});
	
		Assert.assertEquals(404, response.getStatusCode().value());
	}

	@Test
	public void testGetHouryElectricity() {
		List<HourlyElectricity> hourlyElectricity= hourlyElectricityService.getAllHourlyElectricityByPanelId(1L, null).getContent();
		ResponseEntity<List<HourlyElectricity>> response = template.exchange("/api/panels/1234567890123456/hourly",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<HourlyElectricity>>() {
				});
		assertThat(hourlyElectricity, is(response.getBody()));
		Assert.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	public void testNoSerialException() throws Exception {
		HttpEntity<Object> panel = getHttpEntity("{\"id\": \"5\"}");
		String erroMsg = "{\"message\":\"Unable to process this request.\"}";
		ResponseEntity<String> response = template.postForEntity("/api/register", panel, String.class);
		Assert.assertEquals(response.getBody(), erroMsg);

	}

	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
}
