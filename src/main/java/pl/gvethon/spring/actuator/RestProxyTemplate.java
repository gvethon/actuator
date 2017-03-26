package pl.gvethon.spring.actuator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * Created by Tomek on 2017-03-26.
 */
@Component
public class RestProxyTemplate {

	private static final Logger LOGGER = LogManager.getLogger(RestProxyTemplate.class);

	@Value("${proxy.host}") String host;

	@Value("${proxy.port}") String port;

	@Autowired RestTemplate restTemplate;

	@PostConstruct
	public void init() {
		int portNumber = -1;
		try {
			portNumber = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			LOGGER.error("Unable to parse the proxy port number");
		}
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(host, portNumber);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		factory.setProxy(proxy);

		restTemplate.setRequestFactory(factory);
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
}
