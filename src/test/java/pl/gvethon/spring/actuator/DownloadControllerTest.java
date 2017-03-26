package pl.gvethon.spring.actuator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Tomek on 2017-03-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HelloWorldConfiguration.class)
@WebAppConfiguration
public class DownloadControllerTest {

	@Autowired
	WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = webAppContextSetup(context).build();
	}

	@Test
	public void downloadFile() throws Exception {
		mockMvc.perform(get("/getFile"))
				.andExpect(status().isOk());
	}

	@Test
	public void downloadFileOther() throws Exception {
		mockMvc.perform(get("/getFile2"))
				.andExpect(status().isOk());
	}


}