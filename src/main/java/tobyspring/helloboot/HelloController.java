package tobyspring.helloboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class HelloController {

	private final HelloService helloService;
//	private ApplicationContext applicationContext;

//	public HelloController(HelloService helloService, ApplicationContext applicationContext) {
//		this.helloService = helloService;
//
//		System.out.println(applicationContext);
//	}

	public HelloController(HelloService helloService) {
		this.helloService = helloService;
	}

	@GetMapping("/hello")
	public String hello(String name) {
		if (name == null || name.trim().length() == 0) throw new IllegalArgumentException();
//		return helloService.sayHello(Objects.requireNonNull(name));
		return helloService.sayHello(name);
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		System.out.println(applicationContext);
//		this.applicationContext = applicationContext;
//	}
}
