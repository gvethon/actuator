package pl.gvethon.spring.actuator;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;

@Controller
public class DownloadController {

	private static final String IMG = "https://i.ytimg.com/vi/pVrDRLOeMKY/hqdefault.jpg";

	@Autowired RestProxyTemplate proxyTemplate;

	@RequestMapping(value = "getFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response) throws IOException {
		URI uri = UriComponentsBuilder.fromUriString(IMG).build().toUri();
		try (InputStream input = uri.toURL().openStream();
			 OutputStream output = response.getOutputStream()) {
			IOUtils.copy(input, output);
			response.flushBuffer();
		}
	}

	@RequestMapping(value = "getFile2", method = RequestMethod.GET)
	public void downloadFileOther(HttpServletResponse response) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> responseEntity = proxyTemplate.getRestTemplate().exchange(IMG, HttpMethod.GET, entity, byte[].class, "1");

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println(response.getHeaderNames());
			response.getOutputStream().write(responseEntity.getBody());
		}
	}
}
