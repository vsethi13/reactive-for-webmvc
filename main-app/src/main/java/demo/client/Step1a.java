package demo.client;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import demo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

public class Step1a {

	private static final Logger logger = LoggerFactory.getLogger(Step1a.class);

	private static RestTemplate restTemplate = new RestTemplate();

	static {
		String baseUrl = "http://localhost:8081?delay=2";
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
	}


	public static void main(String[] args) {

		Instant start = Instant.now();

//		for (int i = 1; i <= 3; i++) {
//			System.out.println("Getting id=" + i);
//			Person person = restTemplate.getForObject("/person/{id}", Person.class, i);
//			System.out.println("Got " + person);
//		}

		IntStream.rangeClosed(1, 1000).parallel().mapToObj(i -> restTemplate.getForObject("/person/{id}", Person.class, i)).forEach(x -> {});

		logTime(start);
	}


	private static void logTime(Instant start) {
		logger.debug("Total: " + Duration.between(start, Instant.now()).toMillis() + " millis");
	}

}
