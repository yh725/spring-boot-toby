package tobyspring.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class HelloControllerTest {

	@Test
	void helloController() {
		HelloController helloController = new HelloController(name -> name);

		String ret = helloController.hello("Test");

		assertThat(ret).isEqualTo("Test");
	}

	@Test
	void failsHelloController() {
		HelloController helloController = new HelloController(name -> name);

		Assertions.assertThatThrownBy(() -> {
			String ret = helloController.hello(null);
		}).isInstanceOf(IllegalArgumentException.class);

		Assertions.assertThatThrownBy(() -> {
			String ret = helloController.hello("");
		}).isInstanceOf(IllegalArgumentException.class);
	}
}
