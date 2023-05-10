package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationTest {
	@Test
	void configuration() {
//		assertThat(new Common()).isSameAs(new Common());

//		Common common = new Common();
//		assertThat(common).isSameAs(common);

/*
		MyConfig myConfig = new MyConfig();
		Bean1 bean1 = myConfig.bean1();
		Bean2 bean2 = myConfig.bean2();

		assertThat(bean1).isSameAs(bean2);
*/

		AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
		ac.register(MyConfig.class);
		ac.refresh();

		Bean1 bean1 = ac.getBean(Bean1.class);
		Bean2 bean2 = ac.getBean(Bean2.class);

		assertThat(bean1.common).isSameAs(bean2.common);
	}

	@Test
	void proxyCommonMethod() {
		MyConfigProxy myConfigProxy = new MyConfigProxy();

		Bean1 bean1 = myConfigProxy.bean1();
		Bean2 bean2 = myConfigProxy.bean2();

		assertThat(bean1.common).isSameAs(bean2.common);
	}

	static class MyConfigProxy extends MyConfig {
		private Common common;

		@Override
		Common common() {
			if (this.common == null) common = super.common();

			return common;
		}
	}

	@Configuration
	static class MyConfig {
		@Bean
		Common common() {
			return new Common();
		}

		@Bean
		Bean1 bean1() {
			return new Bean1(common());
		}

		@Bean
		Bean2 bean2() {
			return new Bean2(common());
		}
	}

	static class Bean1 {
		private final Common common;

		public Bean1(Common common) {
			this.common = common;
		}
	}

	static class Bean2 {
		private final Common common;

		public Bean2(Common common) {
			this.common = common;
		}
	}

	static class Common {
	}

	// bean1 <-- Common
	// bean2 <-- Common
}
