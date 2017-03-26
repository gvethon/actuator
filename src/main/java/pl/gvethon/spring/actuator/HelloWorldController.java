package pl.gvethon.spring.actuator;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/hello-world")
public class HelloWorldController {
	
	private static final String MESSAGE = "Hello, %s!"; 
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping
	public @ResponseBody Greeting sayHello(@RequestParam(value="name", required=false) String name) {
		return new Greeting(counter.incrementAndGet(), String.format(MESSAGE, name));
	}
	
}
