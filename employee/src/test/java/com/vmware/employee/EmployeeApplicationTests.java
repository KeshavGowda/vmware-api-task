package com.vmware.employee;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeApplicationTests {
	
	@Autowired
	private MockMvc mockvc;

	@Test
	void contextLoads() {
		
	}
	
	/**
	 * Tests a scenario where employee information is successfully saved into database. 
	 * 
	 * @throws Exception
	 */
	@Test
	void testEmployeeLoad() throws Exception {
		Path inputFile = Paths.get("src/test/resources/employees.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("file", "employees", MediaType.APPLICATION_OCTET_STREAM.toString(), Files.readAllBytes(inputFile));
		final MvcResult result = mockvc.perform(MockMvcRequestBuilders.multipart("/api/employee")
				.file(multipartFile)
				.queryParam("action", "upload"))
		.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		String statusUrl = JsonPath.parse(response).read("$.statusCheckUrl");
		//wait until async processing is complete
		Thread.sleep(5000);
		final MvcResult result2 = mockvc.perform(MockMvcRequestBuilders.get(statusUrl)).andExpect(status().isOk()).andReturn();
		String response2 = result2.getResponse().getContentAsString();
		assertThat(response2).isEqualTo("Success");
	}
	
	/**
	 * Tests a scenario where the request fails due to incorrect format in input file. 
	 * 
	 * @throws Exception
	 */
	@Test
	void testIncorrectFileFormat() throws Exception {
		Path inputFile = Paths.get("src/test/resources/employees2.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("file", "employees2", MediaType.APPLICATION_OCTET_STREAM.toString(), Files.readAllBytes(inputFile));
		final MvcResult result = mockvc.perform(MockMvcRequestBuilders.multipart("/api/employee")
				.file(multipartFile)
				.queryParam("action", "upload"))
		.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		String statusUrl = JsonPath.parse(response).read("$.statusCheckUrl");
		//wait until async processing is complete
		Thread.sleep(5000);
		final MvcResult result2 = mockvc.perform(MockMvcRequestBuilders.get(statusUrl)).andExpect(status().isOk()).andReturn();
		String response2 = result2.getResponse().getContentAsString();
		assertThat(response2).isEqualTo("Failed");
	}
	
	/**
	 * Tests a scenario where input file has 1000,000 rows . 
	 * 
	 * @throws Exception
	 */
	@Test
	void testBulkEmployeeLoad() throws Exception {
		Path inputFile = Paths.get("src/test/resources/employees3.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("file", "employees3", MediaType.APPLICATION_OCTET_STREAM.toString(), Files.readAllBytes(inputFile));
		final MvcResult result = mockvc.perform(MockMvcRequestBuilders.multipart("/api/employee")
				.file(multipartFile)
				.queryParam("action", "upload"))
		.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		String statusUrl = JsonPath.parse(response).read("$.statusCheckUrl");
		//wait until async processing is complete
		Thread.sleep(50000);
		final MvcResult result2 = mockvc.perform(MockMvcRequestBuilders.get(statusUrl)).andExpect(status().isOk()).andReturn();
		String response2 = result2.getResponse().getContentAsString();
		assertThat(response2).isEqualTo("Success");
	}
	
	/**
	 * Tests a scenario when status of the incorrect task id is requested. 
	 * 
	 * @throws Exception
	 */
	@Test
	void testIncorrectTaskId() throws Exception {
		mockvc.perform(MockMvcRequestBuilders.get("/api/getStatus/100")).andExpect(status().isBadRequest());	
	}

}
