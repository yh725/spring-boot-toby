package tobyspring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HelloApiTest {

	@Test
	void helloApi() {
		// http localhost:9090/hello?name=Spring
		TestRestTemplate rest = new TestRestTemplate();

		ResponseEntity<String> res =
				rest.getForEntity("http://localhost:9090/app/hello?name={name}", String.class, "Spring");

		// status code 200
		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

		// header(content-type) text/plain
		assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);

		// body Hello Spring
		assertThat(res.getBody()).isEqualTo("*Hello Spring*");
	}

	@Test
	void failsHelloApi() {
		// http localhost:9090/hello?name=
		TestRestTemplate rest = new TestRestTemplate();

		ResponseEntity<String> res =
				rest.getForEntity("http://localhost:9090/app/hello?name=", String.class);

		assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
